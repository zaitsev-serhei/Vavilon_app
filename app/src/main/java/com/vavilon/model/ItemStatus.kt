package com.vavilon.model

enum class ItemStatus( val status:String) {
    PLANNED("PLANNED"),
    INPROCESS("IN PROCESS"),
    OVERDUE("OVERDUE"),
    COMPLETE("COMPLETE");

    fun getItemStatus(): String {
        return status
    }
}