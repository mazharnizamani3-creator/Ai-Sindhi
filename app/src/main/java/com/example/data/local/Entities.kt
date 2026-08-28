package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val isUser: Boolean,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val category: String = "general",
    val isBookmarked: Boolean = false,
    val attachedImageUrl: String? = null
)

@Entity(tableName = "generated_images")
data class GeneratedImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val promptSindhi: String,
    val promptEnglish: String = "",
    val imageBase64: String? = null,
    val imageUrl: String? = null,
    val localUri: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val tag: String = "Sindhi Culture",
    val isFavorite: Boolean = false
)

@Entity(tableName = "agri_guides")
data class AgriGuideEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titleSindhi: String,
    val categorySindhi: String, // فصل، حشرات، پاڻي، ڀاڻ
    val shortSummary: String,
    val fullContent: String,
    val symptoms: String = "",
    val remedies: String = "",
    val chemicalControl: String = "",
    val organicControl: String = "",
    val bestSeason: String = "",
    val isBookmarked: Boolean = false
)
