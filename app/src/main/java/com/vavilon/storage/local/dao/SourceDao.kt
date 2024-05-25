package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.model.SourceCategory
import com.vavilon.storage.local.entities.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Insert
    suspend fun insert(source: Source)
    @Update
    suspend fun update(source: Source)
    @Query("SELECT * FROM sources WHERE isDeleted = 0")
    fun getAllSources(): Flow<List<Source>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY title ASC")
    fun getSourceListSortedAsc(category: String): Flow<List<Source>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY title DESC")
    fun getSourceListSortedDesc(category: String): Flow<List<Source>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY current_balance ASC")
    fun getSourceListSortedBalance(category: String): Flow<List<Source>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY creation_date ASC")
    fun getSourceListSortedLastAdded(category: String): Flow<List<Source>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category")
    fun getSourceListSortedType(category: String): Flow<List<Source>>
}