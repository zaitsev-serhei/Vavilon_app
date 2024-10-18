package com.vavilon.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.CategoryTypes
import com.vavilon.model.TransactionCategories
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.repositories.TransactionRepository
import com.vavilon.model.states.TransactionState
import com.vavilon.storage.local.Converter
import com.vavilon.storage.local.entities.Transaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    private val _category = MutableStateFlow(TransactionCategories.ALL)
    private val _categoryMap = MutableStateFlow(transactionRepository.getCategoriesMap())
    private val _transactionList = _category
        .flatMapLatest { category ->
            when (category) {
                TransactionCategories.ALL -> transactionRepository
                    .getAllTransactionList()

                else -> transactionRepository
                    .getCategorizedTransactionList(category)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TransactionState())
    val state = combine(_state, _transactionList) { state,  categorizedList ->
        Log.d("ViewModel", "Current state category: ${_state.value.transactionCategory}")
        Log.d("ViewModel", "Current _category: ${_category.value}")
        state.copy(
            transactionList = categorizedList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())
    val categoriesMap =
        _categoryMap.value.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    init {
        viewModelScope.launch {
            transactionRepository.getCategoriesMap().collect { categoriesMap ->
                _state.update { state ->
                    state.copy(categoriesList = categoriesMap.keys.toList())
                }
            }
        }
    }

    fun onEvent(event: TransactionEvent) {
        when (event) {
            TransactionEvent.SaveTransaction -> {
                val category = state.value.transactionCategory
                val description = state.value.description
                val amount = state.value.amount
                if (amount <= 0) {
                    return
                }
                viewModelScope.launch {
                    val currentDate = Date()
                    val formattedDate = Converter.dateToTimestamp(currentDate)
                    val transaction =
                        Transaction(amount, category.getTransactionCategory(), description, formattedDate?:"")
                    Log.d("Add transaction", "Before save: ${category.getTransactionCategory()}")
                    transactionRepository.createTransaction(transaction)
                    Log.d("Add transaction", "After save: ${category.getTransactionCategory()}")
                }
                _state.update {
                    TransactionState()
                }
            }


            TransactionEvent.HideDialog -> _state.update {
                TransactionState()
            }

            is TransactionEvent.SetAmount -> _state.update {
                it.copy(
                    amount = event.amount
                )
            }

            is TransactionEvent.SetCategory -> {
                Log.d("ViewModel", "Category before update: ${_state.value.transactionCategory}")
                _state.update {
                    it.copy(transactionCategory = event.category)
                }
                Log.d("ViewModel", "Category after update: ${_state.value.transactionCategory}")
            }

            is TransactionEvent.SetDescription -> _state.update {
                it.copy(
                    description = event.description
                )
            }

            TransactionEvent.ShowDialog -> _state.update {
                it.copy(
                    isAddingNewTransaction = true
                )
            }
        }
    }
}