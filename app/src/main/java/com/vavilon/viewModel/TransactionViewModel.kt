package com.vavilon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.TransactionCategories
import com.vavilon.model.repositories.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    private val _category = MutableStateFlow(TransactionCategories.ALL)
    private val _categorizedList = _category
        .flatMapLatest { category ->
            when (category) {
                TransactionCategories.ALL -> transactionRepository.getAllTransactionList()
                TransactionCategories.RENT -> TODO()
                TransactionCategories.FOOD -> TODO()
                TransactionCategories.TRAVEL -> TODO()
                TransactionCategories.TAXES -> TODO()
                TransactionCategories.HOBBY -> TODO()
                TransactionCategories.RESTAURANTS -> TODO()
                TransactionCategories.ONETIME -> TODO()
                TransactionCategories.INCOME -> TODO()
                TransactionCategories.CUSTOM -> TODO()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}