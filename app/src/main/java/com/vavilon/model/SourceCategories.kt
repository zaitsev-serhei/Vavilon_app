package com.vavilon.model

enum class SourceCategories(val sourceCategory: String) {
    INCOME("Income"),
    EXPENSE("Expense"),
    SAVING("Saving");

    fun getSrcCategory () : String {
        return sourceCategory
    }
}