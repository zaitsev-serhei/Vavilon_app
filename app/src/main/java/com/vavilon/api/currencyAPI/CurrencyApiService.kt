package com.vavilon.api.currencyAPI

import com.vavilon.model.dataHandlers.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val APP_ID = "f011b0310cc24744bc7fdbd8a024429b"
interface CurrencyApiService {
    @GET("/latest.json")
    suspend fun getExchangeRatesForBase(
        @Query("app_id") appId: String = APP_ID,
        @Query("base") baseCurrency: String ="UAH",
        @Query("symbols") symbols:String = "USD,EUR,GBP"
    ):CurrencyResponse
}