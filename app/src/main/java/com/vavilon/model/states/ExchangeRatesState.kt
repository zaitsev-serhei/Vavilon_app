package com.vavilon.model.states

import com.vavilon.model.dataHandlers.ExchangeRate

data class ExchangeRatesState(
    var exchangeRatesList: List<ExchangeRate> = emptyList()
)