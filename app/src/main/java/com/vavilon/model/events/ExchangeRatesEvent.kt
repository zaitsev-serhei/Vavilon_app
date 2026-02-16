package com.vavilon.model.events

sealed interface ExchangeRatesEvent {
    object RefreshCurrencies : ExchangeRatesEvent
}