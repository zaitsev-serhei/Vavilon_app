package com.vavilon.model

enum class TransactionStatus(private val status:String) {
    PLANNED("PLANNED"),
    COMPLETE("COMPLETE");

    fun getTransactionStatus(): String {
        return status
    }
}