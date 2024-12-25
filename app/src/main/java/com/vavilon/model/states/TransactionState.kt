package com.vavilon.model.states

import com.vavilon.model.CategoryTypes
import com.vavilon.model.TransactionCategories
import com.vavilon.model.ItemStatus
import com.vavilon.storage.local.entities.Transaction

data class TransactionState(
    val transactionList: List<Transaction> = emptyList(),
    val categoriesList: List<String> = emptyList(),
    val amount: Double = 0.0,
    val transactionId: Long = 0,
    val description: String = "",
    val status:ItemStatus = ItemStatus.COMPLETE,
    val type: CategoryTypes = CategoryTypes.DEFAULT,
    val transactionCategory: TransactionCategories = TransactionCategories.ALL,
    val isAddingNewTransaction: Boolean = false,
    val isEditingTransaction: Boolean = false
)