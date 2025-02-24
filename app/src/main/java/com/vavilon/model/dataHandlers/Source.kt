package com.vavilon.model.dataHandlers

import com.vavilon.model.SourceCategories

data class Source(
    private var id: Long = 0,
    private var name: String,
    private var description: String,
    private var type: SourceCategories,
    private var currentBalance: Double = 0.0,
    private var totalBalance: Double = 0.0,
    private var relatedTransactionList: List<Transaction> = emptyList()
) {
    fun getId(): Long = id
    fun setId(id: Long) {
        this.id = id
    }

    fun getName(): String = name
    fun setName(name: String) {
        this.name = name
    }

    fun getDescription(): String = description
    fun setDescription(description: String) {
        this.description = description
    }

    fun getType(): SourceCategories = type
    fun setType(type: SourceCategories) {
        this.type = type
    }

    fun getCurrentBalance(): Double = currentBalance
    fun setCurrentBalance(currentBalance: Double) {
        this.currentBalance = currentBalance
    }

    fun getTotalBalance(): Double = totalBalance
    fun setTotalBalance(totalBalance: Double) {
        this.totalBalance = totalBalance
    }

    fun getRelatedTransactions(): List<Transaction> = relatedTransactionList
    fun setRelatedTransactions(transactionList: List<Transaction>) {
        this.relatedTransactionList = transactionList
    }
}