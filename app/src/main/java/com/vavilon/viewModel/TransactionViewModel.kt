package com.vavilon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.CategoryTypes
import com.vavilon.model.TransactionCategories
import com.vavilon.model.events.TransactionEvent
import com.vavilon.model.repositories.TransactionRepository
import com.vavilon.model.states.TransactionState
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
                TransactionCategories.ALL -> transactionRepository.getAllTransactionList()
                TransactionCategories.RENT -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.FOOD -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.TRAVEL -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.TAXES -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.HOBBY -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.RESTAURANTS -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.ONETIME -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.INCOME -> transactionRepository.getCategorizedTransactionList(
                    category
                )

                TransactionCategories.CUSTOM -> transactionRepository.getCategorizedTransactionList(
                    category
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TransactionState())
    val state = combine(_state, _category, _transactionList) { state, category, categorizedList ->
        state.copy(
            transactionCategory = category,
            transactionList = categorizedList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())
    val categoriesMap =
        _categoryMap.value.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    fun OnEvent(event: TransactionEvent) {
        when (event) {
            TransactionEvent.SaveTransaction -> {
                val category = state.value.transactionCategory
                val description = state.value.description
                val amount = state.value.amount
                if (amount <= 0) {
                    return
                } else {
                    viewModelScope.launch {
                        val transaction = Transaction(amount, category.getTransactionCategory(), description, Date())
                        transactionRepository.createTransaction(transaction)
                    }
                    _state.update {
                        it.copy(
                            amount = 0.0,
                            transactionId = 0,
                            description = "",
                            type = CategoryTypes.DEFAULT,
                            transactionCategory = TransactionCategories.ALL,
                            isAddingNewTransaction = false
                        )
                    }
                }
            }

            TransactionEvent.HideDialog -> _state.update {
                it.copy(
                    isAddingNewTransaction = false
                )
            }

            is TransactionEvent.SetAmount -> _state.update {
                it.copy(
                    amount = event.amount
                )
            }

            is TransactionEvent.SetCategory -> _state.update {
                it.copy(
                    transactionCategory = event.category
                )
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