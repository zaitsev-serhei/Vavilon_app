package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.Transaction

@Dao
interface TransactionDao {
    @Insert
    fun insert(transaction: Transaction)
    @Update
    fun update(transaction: Transaction)
    @Query("SELECT * FROM transactions WHERE source_id = :sourceId")
    fun getTransactionsBySource(sourceId: Long): List<Transaction>?
}