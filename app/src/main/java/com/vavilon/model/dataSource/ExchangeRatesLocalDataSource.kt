package com.vavilon.model.dataSource

import com.vavilon.model.dataHandlers.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesLocalDataSource {
    suspend fun createRate(rate: ExchangeRate)
    suspend fun updateRate(rate: ExchangeRate)
    suspend fun createRatesFromList(rates:List<ExchangeRate>)
    fun getAllRates(): Flow<List<ExchangeRate>>
    suspend fun clearRates()
}