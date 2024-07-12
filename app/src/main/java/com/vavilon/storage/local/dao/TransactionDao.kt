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
            + "JOIN tr_categories ON transactions.category_id = tr_categories.category_id "
            + "WHERE tr_categories.title = :category")
    fun getTransactionsByCategory(category:String): Flow<List<Transaction>>
}