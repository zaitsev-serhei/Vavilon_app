package com.vavilon.services

interface ExchangeRatesService {
    suspend fun fetchExchangeRates()
}