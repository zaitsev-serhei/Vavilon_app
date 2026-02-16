package com.vavilon.services.impl

import android.util.Log
import com.vavilon.model.dataHandlers.ExchangeRate
import com.vavilon.model.dataSource.ExchangeRatesLocalDataSource
import com.vavilon.services.ExchangeRatesService
import com.vavilon.storage.local.dao.CurrencyDao
import com.vavilon.storage.local.entities.ExchangeRatesEntity
import com.vavilon.utils.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExchangeRatesServiceImpl @Inject constructor(
    private val exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
    private val retrofitInstance: RetrofitInstance
) : ExchangeRatesService {
    val exchangeRateList: Flow<List<ExchangeRate>> = exchangeRatesLocalDataSource.getAllRates()
    override suspend fun fetchExchangeRates() {
        try {
            val response = retrofitInstance.api.getExchangeRatesForBase()
            Log.d("Rates Service", "Rates response [$response]")
            Log.d("Rates Service", "Raw rates: ${response.rates}")
            val ratesList = response.rates.map { (currency, rate) ->
                ExchangeRate(
                    baseCurrencyCode = response.base,
                    baseCurrencyLabel = "",
                    relatedCurrencyCode = currency,
                    relatedCurrencyLabel = "",
                    rate = rate,
                    updateDate = Date(),
                    isUpdated = true
                )
            }
            Log.d("Rates Service", "Rates list after response [$ratesList]")
            exchangeRatesLocalDataSource.clearRates()
            exchangeRatesLocalDataSource.createRatesFromList(ratesList)
        }
        catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}