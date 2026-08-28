package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.BuildConfig
import com.example.data.api.GeminiClient
import com.example.data.knowledge.SindhiAgriKnowledgeBase
import com.example.data.local.AgriGuideDao
import com.example.data.local.AgriGuideEntity
import com.example.data.local.ChatDao
import com.example.data.local.ChatMessageEntity
import com.example.data.local.GeneratedImageEntity
import com.example.data.local.ImageDao
import com.example.data.model.GeminiContent
import com.example.data.model.GeminiGenerationConfig
import com.example.data.model.GeminiImageConfig
import com.example.data.model.GeminiPart
import com.example.data.model.GeminiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SindhiAssistantRepository(
    private val chatDao: ChatDao,
    private val imageDao: ImageDao,
    private val agriGuideDao: AgriGuideDao,
    private val context: Context
) {
    val allMessages: Flow<List<ChatMessageEntity>> = chatDao.getAllMessages()
    val bookmarkedMessages: Flow<List<ChatMessageEntity>> = chatDao.getBookmarkedMessages()
    val allImages: Flow<List<GeneratedImageEntity>> = imageDao.getAllGeneratedImages()
    val allGuides: Flow<List<AgriGuideEntity>> = agriGuideDao.getAllGuides()

    suspend fun initializeDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        try {
            if (agriGuideDao.getGuideCount() == 0) {
                agriGuideDao.insertGuides(SindhiAgriKnowledgeBase.defaultGuides)
                // Insert initial professional welcome message
                chatDao.insertMessage(
                    ChatMessageEntity(
                        isUser = false,
                        messageText = "م خوش آمديد! مان سنڌي داناءُ AI آهيان. زراعت، فني معلومات يا ڪنهن به سوال لاءِ مان حاضر آهيان.",
                        category = "welcome"
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("SindhiRepo", "Error initializing DB: ${e.message}")
        }
    }

    suspend fun sendMessage(userPrompt: String, history: List<ChatMessageEntity> = emptyList()): String = withContext(Dispatchers.IO) {
        val userMsg = ChatMessageEntity(isUser = true, messageText = userPrompt)
        chatDao.insertMessage(userMsg)

        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        var responseText: String

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val contentsList = mutableListOf<GeminiContent>()

                val recentHistory = history.takeLast(6)
                for (msg in recentHistory) {
                    contentsList.add(
                        GeminiContent(
                            role = if (msg.isUser) "user" else "model",
                            parts = listOf(GeminiPart(text = msg.messageText))
                        )
                    )
                }

                contentsList.add(
                    GeminiContent(
                        role = "user",
                        parts = listOf(GeminiPart(text = userPrompt))
                    )
                )

                val request = GeminiRequest(
                    contents = contentsList,
                    systemInstruction = GeminiContent(
                        parts = listOf(GeminiPart(text = SindhiAgriKnowledgeBase.SYSTEM_PROMPT))
                    ),
                    generationConfig = GeminiGenerationConfig(
                        temperature = 0.7f,
                        topP = 0.95f,
                        topK = 40
                    )
                )

                val apiResponse = GeminiClient.service.generateContent(apiKey, request)
                val rawText = apiResponse.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

                // Removed forced repetitive greeting prefix completely for a professional look
                if (!rawText.isNullOrBlank()) {
                    responseText = rawText
                } else {
                    responseText = SindhiAgriKnowledgeBase.findMatchingResponse(userPrompt)
                }
            } catch (e: Exception) {
                Log.w("SindhiRepo", "API call fallback to knowledge engine: ${e.message}")
                responseText = SindhiAgriKnowledgeBase.findMatchingResponse(userPrompt)
            }
        } else {
            responseText = SindhiAgriKnowledgeBase.findMatchingResponse(userPrompt)
        }

        val aiMsg = ChatMessageEntity(isUser = false, messageText = responseText)
        chatDao.insertMessage(aiMsg)
        return@withContext responseText
    }

    suspend fun generateImage(promptSindhi: String, promptEnglish: String = "", tag: String = "Cultural"): GeneratedImageEntity = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        val effectivePrompt = if (promptEnglish.isNotBlank()) promptEnglish else promptSindhi
        var base64Data: String? = null

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val request = GeminiRequest(
                    contents = listOf(
                        GeminiContent(
                            parts = listOf(GeminiPart(text = "Generate an image: $effectivePrompt"))
                        )
                    ),
                    generationConfig = GeminiGenerationConfig(
                        imageConfig = GeminiImageConfig(aspectRatio = "1:1", imageSize = "1K"),
                        responseModalities = listOf("TEXT", "IMAGE")
                    )
                )

                // Fixed to use standard generateContent endpoint which supports multimodal outputs properly
                val response = GeminiClient.service.generateContent(apiKey, request)
                val inline = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull { it.inlineData != null }?.inlineData
                if (inline != null) {
                    base64Data = inline.data
                }
            } catch (e: Exception) {
                Log.w("SindhiRepo", "Image generation API error: ${e.message}")
            }
        }

        val entity = GeneratedImageEntity(
            promptSindhi = promptSindhi,
            promptEnglish = promptEnglish,
            imageBase64 = base64Data,
            tag = tag
        )
        val id = imageDao.insertImage(entity)
        return@withContext entity.copy(id = id)
    }

    suspend fun toggleMessageBookmark(id: Long, current: Boolean) = withContext(Dispatchers.IO) {
        chatDao.updateBookmark(id, !current)
    }

    suspend fun toggleGuideBookmark(id: Long, current: Boolean) = withContext(Dispatchers.IO) {
        agriGuideDao.updateGuideBookmark(id, !current)
    }

    suspend fun toggleImageFavorite(id: Long, current: Boolean) = withContext(Dispatchers.IO) {
        imageDao.updateFavorite(id, !current)
    }

    suspend fun deleteMessage(id: Long) = withContext(Dispatchers.IO) {
        chatDao.deleteMessage(id)
    }

    suspend fun clearHistory() = withContext(Dispatchers.IO) {
        chatDao.clearHistory()
        chatDao.insertMessage(
            ChatMessageEntity(
                isUser = false,
                messageText = "ڳالهه ٻولهه نئين سر شروع ڪئي وئي آهي. مان اوهان جي خدمت لاءِ حاضر آهيان.",
                category = "welcome"
            )
        )
    }

    fun searchAgriGuides(query: String): Flow<List<AgriGuideEntity>> {
        return agriGuideDao.searchGuides(query)
    }

    fun getGuidesByCategory(category: String): Flow<List<AgriGuideEntity>> {
        return agriGuideDao.getGuidesByCategory(category)
    }
}
