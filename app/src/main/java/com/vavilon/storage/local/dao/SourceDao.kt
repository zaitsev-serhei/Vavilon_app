package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.SourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: SourceEntity)
    @Update
    suspend fun update(source: SourceEntity)
    @Query("SELECT * FROM sources WHERE source_id= :sourceId")
    fun getSource(sourceId: Long): Flow<SourceEntity>
    @Query("SELECT * FROM sources WHERE isDeleted = 0")
    fun getAllSources(): Flow<List<SourceEntity>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY title ASC")
    fun getSourceListSortedAsc(category: String): Flow<List<SourceEntity>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY title DESC")
    fun getSourceListSortedDesc(category: String): Flow<List<SourceEntity>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY current_balance ASC")
    fun getSourceListSortedBalance(category: String): Flow<List<SourceEntity>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category ORDER BY creation_date ASC")
    fun getSourceListSortedLastAdded(category: String): Flow<List<SourceEntity>>
    @Query("SELECT * FROM sources WHERE isDeleted = 0 AND type = :category")
    fun getSourceListSortedType(category: String): Flow<List<SourceEntity>>
    @Query("SELECT type, SUM(current_balance) AS total  FROM sources WHERE isDeleted = 0 GROUP BY type")
    fun getTotals():Flow<Map<@MapColumn(columnName = "type")String,@MapColumn(columnName = "total")Double>>
    @Query("SELECT type, COUNT(*) AS count FROM sources WHERE isDeleted = 0 GROUP BY type")
    fun getSourceCounter():Flow<Map<@MapColumn(columnName = "type")String,@MapColumn(columnName = "count")Int>>
}