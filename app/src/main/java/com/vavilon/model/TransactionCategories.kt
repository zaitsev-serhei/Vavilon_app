package com.vavilon.model

enum class TransactionCategories (val category:String) {
    ALL("All"),
    RENT("Rent"),
    FOOD("Food"),
    TRAVEL("Travel"),
    TAXES("Taxes"),
    HOBBY("Hobby"),
    RESTAURANTS("Restaurants"),
    ONETIME("One-time"),
    INCOME("Income"),
    CUSTOM("Custom");
    fun getTransactionCategory () : String {
        return category
    }
}