package com.vavilon.model.events

sealed class UserEvent {
    data class SourceEventWrapper(val event: SourceEvent) : UserEvent()
    data class TransactionEventWrapper(val event: TransactionEvent) : UserEvent()
}