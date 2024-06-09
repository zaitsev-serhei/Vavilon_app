package com.vavilon.model.events

import com.vavilon.model.TransactionCategories
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction

sealed interface TransactionEvent {
    object SaveTransaction : TransactionEvent
    object ShowDialog : TransactionEvent
    object HideDialog : TransactionEvent
    data class SetDescription(val name: String) : TransactionEvent
    data class SetAmount(val amount: Double) : TransactionEvent
    data class SetCategory(val category: TransactionCategories) : TransactionEvent
    data class FilterBySource(val source: Source) : TransactionEvent
}