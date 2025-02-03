package com.vavilon.model

enum class ItemStatus( val status:String) {
    PLANNED("PLANNED"),
    INPROCESS("INPROCESS"),
    OVERDUE("OVERDUE"),
    COMPLETE("COMPLETE");

    fun getItemStatus(): String {
        return status
    }
}