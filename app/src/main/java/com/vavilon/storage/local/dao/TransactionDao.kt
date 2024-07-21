package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)
    @Update
    suspend fun update(transaction: Transaction)
    @Query("SELECT * FROM transactions ORDER BY creation_date ASC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions "
            + "WHERE transactions.category_name = :category")
    fun getTransactionsByCategory(category:String): Flow<List<Transaction>>
}