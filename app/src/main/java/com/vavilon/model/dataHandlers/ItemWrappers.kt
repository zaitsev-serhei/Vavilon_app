package com.vavilon.model.dataHandlers

import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import java.util.Date

data class SourceItemWrapper(val source: Source): EntityItem {
    override val title: String
        get() = source.sourceTitle
    override val description: String
        get() = source.sourceDescription
    override val type: String
        get() = source.sourceType
    override val balance: Double
        get() = source.currentBalance
    override val creationDate: String
        get() = source.creationDate
}

data class TransactionItemWrapper(val transaction:Transaction): EntityItem {
    override val title: String
        get() = transaction.category
    override val description: String
        get() = transaction.description
    override val type: String
        get() = transaction.category
    override val balance: Double
        get() = transaction.amount
    override val creationDate: String
        get() = transaction.transactionDate
}