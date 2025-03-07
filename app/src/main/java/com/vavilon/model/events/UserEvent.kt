package com.vavilon.model.events

sealed class UserEvent {
    data class SourceEventWrapper(val event: SourceEvent) : UserEvent()
    data class TransactionEventWrapper(val event: TransactionEvent) : UserEvent()
    data class PlanEventWrapper(val event: PlanEvent) : UserEvent()
    data class ExchangeRatesEventWrapper(val event:ExchangeRatesEvent) : UserEvent()
}