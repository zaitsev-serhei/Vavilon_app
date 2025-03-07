package com.vavilon.model.dataSource.impl

import android.util.Log
import com.vavilon.model.dataHandlers.ExchangeRate
import com.vavilon.model.dataHandlers.toEntity
import com.vavilon.model.dataHandlers.toModel
import com.vavilon.model.dataSource.ExchangeRatesLocalDataSource
import com.vavilon.model.repositories.ExchangeRateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExchangeRatesLocalDataSourceImpl @Inject constructor(private val ratesRepository: ExchangeRateRepository) :
    ExchangeRatesLocalDataSource {
    override suspend fun createRate(rate: ExchangeRate) {
        ratesRepository.createRate(rate.toEntity())
    }

    override suspend fun updateRate(rate: ExchangeRate) {
        ratesRepository.updateRate(rate.toEntity())
    }

    override suspend fun createRatesFromList(rates: List<ExchangeRate>) {
        Log.d("Local Data","Call create rates fro list $rates")
        ratesRepository.createRates(rates.map { rate ->
            rate.toEntity()
        })
    }

    override fun getAllRates(): Flow<List<ExchangeRate>> {
        return ratesRepository.getAllRates().map { ratesList->
            ratesList.map { entity->
                entity.toModel()
            }
        }
    }

    override suspend fun clearRates() {
        ratesRepository.clearRates()
    }
}