package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AgriGuideEntity
import com.example.data.local.AppDatabase
import com.example.data.local.ChatMessageEntity
import com.example.data.local.GeneratedImageEntity
import com.example.data.repository.SindhiAssistantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ImageGenUiState {
    object Idle : ImageGenUiState()
    object Loading : ImageGenUiState()
    data class Success(val imageEntity: GeneratedImageEntity, val bitmap: Bitmap?) : ImageGenUiState()
    data class Error(val messageSindhi: String) : ImageGenUiState()
}

class SindhiAssistantViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = SindhiAssistantRepository(
        chatDao = database.chatDao(),
        imageDao = database.imageDao(),
        agriGuideDao = database.agriGuideDao(),
        context = application
    )

    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.allMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarkedMessages: StateFlow<List<ChatMessageEntity>> = repository.bookmarkedMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allGeneratedImages: StateFlow<List<GeneratedImageEntity>> = repository.allImages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allGuides: StateFlow<List<AgriGuideEntity>> = repository.allGuides
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Chat UI state
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private val _currentInput = MutableStateFlow("")
    val currentInput: StateFlow<String> = _currentInput.asStateFlow()

    // Agri search & category filter
    private val _selectedAgriCategory = MutableStateFlow("سڀ")
    val selectedAgriCategory: StateFlow<String> = _selectedAgriCategory.asStateFlow()

    private val _agriSearchQuery = MutableStateFlow("")
    val agriSearchQuery: StateFlow<String> = _agriSearchQuery.asStateFlow()

    val filteredGuides: StateFlow<List<AgriGuideEntity>> = combine(
        allGuides,
        selectedAgriCategory,
        agriSearchQuery
    ) { guides, category, query ->
        guides.filter { guide ->
            val matchCategory = category == "سڀ" || guide.categorySindhi == category
            val matchQuery = query.isBlank() ||
                    guide.titleSindhi.contains(query, ignoreCase = true) ||
                    guide.fullContent.contains(query, ignoreCase = true) ||
                    guide.symptoms.contains(query, ignoreCase = true)
            matchCategory && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Image Generator UI state
    private val _imageGenState = MutableStateFlow<ImageGenUiState>(ImageGenUiState.Idle)
    val imageGenState: StateFlow<ImageGenUiState> = _imageGenState.asStateFlow()

    private val _imagePromptSindhi = MutableStateFlow("")
    val imagePromptSindhi: StateFlow<String> = _imagePromptSindhi.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeDatabaseIfNeeded()
        }
    }

    fun onInputChange(text: String) {
        _currentInput.value = text
    }

    fun onImagePromptChange(text: String) {
        _imagePromptSindhi.value = text
    }

    fun onCategorySelected(category: String) {
        _selectedAgriCategory.value = category
    }

    fun onAgriSearchChange(query: String) {
        _agriSearchQuery.value = query
    }

    fun sendMessage(customPrompt: String? = null) {
        val textToSend = (customPrompt ?: _currentInput.value).trim()
        if (textToSend.isBlank() || _isChatLoading.value) return

        if (customPrompt == null) {
            _currentInput.value = ""
        }

        _isChatLoading.value = true
        viewModelScope.launch {
            try {
                repository.sendMessage(textToSend, chatMessages.value)
            } catch (e: Exception) {
                // Safe handling
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun generateImage(sindhiPrompt: String? = null, englishPrompt: String = "", tag: String = "Cultural") {
        val prompt = (sindhiPrompt ?: _imagePromptSindhi.value).trim()
        if (prompt.isBlank() || _imageGenState.value is ImageGenUiState.Loading) return

        _imageGenState.value = ImageGenUiState.Loading
        viewModelScope.launch {
            try {
                val entity = repository.generateImage(prompt, englishPrompt, tag)
                val bitmap = if (!entity.imageBase64.isNullOrBlank()) {
                    decodeBase64ToBitmap(entity.imageBase64)
                } else {
                    null
                }
                _imageGenState.value = ImageGenUiState.Success(entity, bitmap)
            } catch (e: Exception) {
                _imageGenState.value = ImageGenUiState.Error("معاف ڪجو سائين! تصوير ٺاهڻ دوران نقص آيو. مهرباني ڪري ٻيهر ڪوشش ڪريو.")
            }
        }
    }

    fun toggleMessageBookmark(message: ChatMessageEntity) {
        viewModelScope.launch {
            repository.toggleMessageBookmark(message.id, message.isBookmarked)
        }
    }

    fun toggleGuideBookmark(guide: AgriGuideEntity) {
        viewModelScope.launch {
            repository.toggleGuideBookmark(guide.id, guide.isBookmarked)
        }
    }

    fun toggleImageFavorite(image: GeneratedImageEntity) {
        viewModelScope.launch {
            repository.toggleImageFavorite(image.id, image.isFavorite)
        }
    }

    fun clearChatHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    private fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}
