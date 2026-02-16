package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.entities.ExchangeRatesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateRepository @Inject constructor(private val currencyDao: CurrencyDao) {
    private val exchangeRatesList = currencyDao.getAll()
    suspend fun createRate(ratesEntity: ExchangeRatesEntity){
        withContext(Dispatchers.IO){
            currencyDao.insert(ratesEntity)
        }
    }
    suspend fun createRates(ratesList:List<ExchangeRatesEntity>){
        withContext(Dispatchers.IO){
            currencyDao.insertRates(ratesList)
        }
    }
    suspend fun updateRate(ratesEntity: ExchangeRatesEntity){
        withContext(Dispatchers.IO){
            currencyDao.update(ratesEntity)
        }
    }
    fun getAllRates() = exchangeRatesList
    suspend fun clearRates(){
        withContext(Dispatchers.IO) {
            currencyDao.clearRates()
        }
    }
}