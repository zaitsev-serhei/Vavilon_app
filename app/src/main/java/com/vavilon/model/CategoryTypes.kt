package com.vavilon.model

enum class CategoryTypes(val type: String) {
    DEFAULT("Default"),
    CUSTOM("Custom");

    fun getCategoryType(): String {
        return type
    }
}