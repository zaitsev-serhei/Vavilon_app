package com.vavilon.viewModel

import androidx.lifecycle.ViewModel
import com.vavilon.model.repositories.TransactionRepository
import javax.inject.Inject

class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {

}