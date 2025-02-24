package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity):Long
    @Update
    suspend fun update(transaction: TransactionEntity)
    @Query("INSERT INTO transaction_for_source (source_id,transaction_id) " +
            "VALUES(:sourceId,:transactionId)")
    suspend fun insertTransactionForSource(sourceId:Long,transactionId:Long)
    @Query("INSERT INTO transaction_for_plan (plan_id,transaction_id) " +
            "VALUES(:planId,:transactionId)")
    suspend fun insertTransactionForPlan(planId:Long,transactionId:Long)

    @Query("SELECT * FROM transactions ORDER BY creation_date ASC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions "
            + "WHERE transactions.category_name = :category")
    fun getTransactionsByCategory(category:String): Flow<List<TransactionEntity>>

    /*@Query("SELECT * FROM transactions " +
            "WHERE source_id = :sourceId")
    fun getTransactionsForSource(sourceId:Long):Flow<List<Transaction>>*/
}