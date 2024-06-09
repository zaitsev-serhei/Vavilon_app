package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.vavilon.storage.local.entities.TransactionCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryDao {
    @Insert
    suspend fun insert(category: TransactionCategory)
    @Update
    suspend fun update(category: TransactionCategory)
}