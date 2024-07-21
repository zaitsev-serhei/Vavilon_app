package com.vavilon.model.repositories

import com.vavilon.model.TransactionCategories
import com.vavilon.storage.local.dao.TransactionCategoryDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.Transaction
import com.vavilon.storage.local.entities.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao,
    private val transactionCategoryDao: TransactionCategoryDao) {
    private val categoriesMap = transactionCategoryDao.getTransactionCategoriesMap()
    private val transactionList = transactionDao.getAllTransactions()
    fun getAllTransactionList() = transactionList

    fun getCategoriesMap() = categoriesMap
    suspend fun createTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(transaction)
        }
    }

    fun getCategorizedTransactionList(category: TransactionCategories): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(category.getTransactionCategory())
    }

    suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.update(transaction)
        }
    }

}