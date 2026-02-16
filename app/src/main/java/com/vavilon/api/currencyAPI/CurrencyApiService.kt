package com.vavilon.api.currencyAPI

import com.vavilon.model.dataHandlers.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val APP_ID = "f011b0310cc24744bc7fdbd8a024429b"
interface CurrencyApiService {
    @GET("/latest.json")
    suspend fun getExchangeRatesForBase(
        @Query("app_id") appId: String = APP_ID,
        //current API_ID supports base = USD only cause it`s free
        @Query("base") baseCurrency: String ="USD",
        @Query("symbols") symbols:String = "UAH,EUR,GBP"
    ):CurrencyResponse
}