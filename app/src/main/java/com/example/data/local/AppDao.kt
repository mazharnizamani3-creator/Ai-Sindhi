package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity): Long

    @Query("UPDATE chat_messages SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Long, isBookmarked: Boolean)

    @Query("SELECT * FROM chat_messages WHERE isBookmarked = 1 ORDER BY timestamp DESC")
    fun getBookmarkedMessages(): Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_messages WHERE id = :id")
    suspend fun deleteMessage(id: Long)

    @Query("DELETE FROM chat_messages")
    suspend fun clearHistory()
}

@Dao
interface ImageDao {
    @Query("SELECT * FROM generated_images ORDER BY timestamp DESC")
    fun getAllGeneratedImages(): Flow<List<GeneratedImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: GeneratedImageEntity): Long

    @Query("UPDATE generated_images SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("SELECT * FROM generated_images WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteImages(): Flow<List<GeneratedImageEntity>>

    @Query("DELETE FROM generated_images WHERE id = :id")
    suspend fun deleteImage(id: Long)
}

@Dao
interface AgriGuideDao {
    @Query("SELECT * FROM agri_guides ORDER BY id ASC")
    fun getAllGuides(): Flow<List<AgriGuideEntity>>

    @Query("SELECT * FROM agri_guides WHERE categorySindhi = :category ORDER BY id ASC")
    fun getGuidesByCategory(category: String): Flow<List<AgriGuideEntity>>

    @Query("SELECT * FROM agri_guides WHERE titleSindhi LIKE '%' || :query || '%' OR fullContent LIKE '%' || :query || '%'")
    fun searchGuides(query: String): Flow<List<AgriGuideEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuides(guides: List<AgriGuideEntity>)

    @Query("UPDATE agri_guides SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateGuideBookmark(id: Long, isBookmarked: Boolean)

    @Query("SELECT * FROM agri_guides WHERE isBookmarked = 1")
    fun getBookmarkedGuides(): Flow<List<AgriGuideEntity>>

    @Query("SELECT COUNT(*) FROM agri_guides")
    suspend fun getGuideCount(): Int
}
