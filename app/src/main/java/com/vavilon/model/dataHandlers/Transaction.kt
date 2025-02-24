package com.vavilon.model.dataHandlers

import com.vavilon.model.ItemStatus
import com.vavilon.model.TransactionCategories
import java.text.SimpleDateFormat
import java.util.Date

data class Transaction(
    private var id: Long = 0,
    private var sourceId: Long,
    private var amount: Double,
    private var description: String,
    private var status: ItemStatus = ItemStatus.COMPLETE,
    private var creationDate: Date,
    private var transactionDate: Date,
    private var plannedOccurrenceDate: Date,
    private var category: TransactionCategories,
) {
    fun getId(): Long = id
    fun setId(id: Long) {
        this.id = id
    }

    fun getSourceId(): Long = sourceId
    fun setSourceId(source: Long) {
        this.sourceId = source
    }

    fun getValue(): Double = amount
    fun setValue(value: Double) {
        this.amount = value
    }

    fun getDescription(): String = description
    fun setDescription(description: String) {
        this.description = description
    }

    fun getStatus(): ItemStatus = status
    fun setStatus(status: ItemStatus) {
        this.status = status
    }

    fun getCreationDate(): Date = creationDate
    fun setCreationDate(creationDate: Date) {
        this.creationDate = creationDate
    }

    fun getTransactionDate(): Date = transactionDate
    fun setTransactionDate(transactionDate: Date) {
        this.transactionDate = transactionDate
    }

    fun getPlannedOccurrenceDate(): Date = plannedOccurrenceDate
    fun setPlannedOccurrenceDate(plannedOccurrenceDate: Date) {
        this.plannedOccurrenceDate = plannedOccurrenceDate
    }

    fun getCategory(): TransactionCategories = category
    fun setCategory(category: TransactionCategories) {
        this.category = category
    }
}