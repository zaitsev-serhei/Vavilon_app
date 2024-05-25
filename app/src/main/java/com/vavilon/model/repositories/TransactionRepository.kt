package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.dao.TransactionDao
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao, private val  currencyDao: CurrencyDao) {
}