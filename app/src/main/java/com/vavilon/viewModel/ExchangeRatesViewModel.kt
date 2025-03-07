package com.vavilon.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.dataSource.ExchangeRatesLocalDataSource
import com.vavilon.model.events.ExchangeRatesEvent
import com.vavilon.model.states.ExchangeRatesState
import com.vavilon.services.ExchangeRatesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRatesViewModel @Inject constructor(
    private val exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
    private val ratesService: ExchangeRatesService
) : ViewModel() {
    private val _state = MutableStateFlow(ExchangeRatesState())
    val state: StateFlow<ExchangeRatesState> = _state.asStateFlow()

    init {
        Log.d("Rate ViewModel Init", "Rates list [${state.value.exchangeRatesList}]")
        viewModelScope.launch {
            ratesService.fetchExchangeRates()
            exchangeRatesLocalDataSource.getAllRates().collect { list ->
                _state.value.exchangeRatesList = list
            }
            Log.d("Rate ViewModel Init", "Updated [${state.value.exchangeRatesList}]")

        }
    }
    fun OnEvent(event: ExchangeRatesEvent){
        when(event) {
            ExchangeRatesEvent.RefreshCurrencies -> refreshRates()
        }

    }
    private fun refreshRates() {
        viewModelScope.launch {
            ratesService.fetchExchangeRates()
        }
    }
}