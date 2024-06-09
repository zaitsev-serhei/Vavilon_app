package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {
    private val transactionList = transactionDao.getAllTransactions()
    fun getAllTransactionList() = transactionList

    suspend fun createTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(transaction)
        }
    }

    suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.update(transaction)
        }
    }

}