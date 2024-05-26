package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {
    private val transactionList = transactionDao.getAllTransactions()
    fun getAllTransactionList() = transactionList

    fun createTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }

}