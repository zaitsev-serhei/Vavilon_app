package com.vavilon.model.dataHandlers

import java.util.Date

data class ExchangeRate(
    var baseCurrencyCode:String,
    var baseCurrencyLabel:String?,
    var relatedCurrencyCode:String,
    var relatedCurrencyLabel:String?,
    var rate:Double,
    var updateDate: Date,
    var isUpdated: Boolean
)
