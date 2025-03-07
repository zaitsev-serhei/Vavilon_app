package com.vavilon.model.dataHandlers

data class CurrencyResponse(
    val date: String,
    val base: String,
    val rates: Map<String, Double>
)
