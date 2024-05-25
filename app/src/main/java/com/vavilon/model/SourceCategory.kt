package com.vavilon.model

enum class SourceCategory(val sourceCategory: String) {
    INCOME("Income"),
    EXPENSE("Expense"),
    SAVING("Saving");

    fun getSrcCategory () : String {
        return sourceCategory
    }
}