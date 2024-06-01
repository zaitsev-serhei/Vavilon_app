package com.vavilon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.SortTypes
import com.vavilon.model.SourceCategories
import com.vavilon.model.events.SourceEvent
import com.vavilon.model.repositories.SourceRepository
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
class SourceViewModel @Inject constructor(private val sourceRepository: SourceRepository) : ViewModel() {
    private val _sortTypes = MutableStateFlow(SortTypes.ASC)
    private val _category = MutableStateFlow(SourceCategories.INCOME)
    private val _sourceListSorted = _category
        .flatMapLatest { category ->
            when(category) {
                SourceCategories.INCOME -> sourceRepository.getSourceListAsc(category)
                SourceCategories.EXPENSE -> sourceRepository.getSourceListAsc(category)
                SourceCategories.SAVING -> sourceRepository.getSourceListAsc(category)
            }

        }
        /*.flatMapLatest { sortType ->
            when(sortType) {
                ///check if category needed here
                SortType.ASC -> sourceRepository.getSourceListAsc(_category.value)
                SortType.DESC -> sourceRepository.getSourceListDesc(_category.value)
                SortType.BALANCE -> sourceRepository.getSourceListSortedBalanceAsc(_category.value)
                SortType.LAST_ADDED -> sourceRepository.getSourceListCreationDate(_category.value)
                SortType.CATEGORY_NAME -> sourceRepository.getSourceListSortedType(_category.value)
            }
        }*/
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(SourceState())
    val state = combine(_state, _category, _sourceListSorted ) {
        state, category,sourceList ->
        state.copy(
            sourceCategory = category,
            sourceList = sourceList,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SourceState())

    fun onEvent(event:SourceEvent) {
        when(event) {
            is SourceEvent.DeleteSource -> {
                viewModelScope.launch {
                    sourceRepository.deleteSource(event.source)
                }
            }
            SourceEvent.HideDialog -> {
                _state.update { it.copy( isAddingNewSource = false) }
            }
            SourceEvent.SaveSource -> {
                val name = state.value.name
                val description = state.value.description
                val sourceType = state.value.sourceCategory
                val balance = state.value.balance
                if(name.isBlank()||description.isBlank()){
                    return
                }
                val source = Source(sourceType.getSrcCategory(),name,description,balance)
                viewModelScope.launch {
                    sourceRepository.createSource(source)
                }
                _state.update { it.copy(
                    isAddingNewSource = false,
                    name = "",
                    sourceCategory = SourceCategories.INCOME,
                    description = "",
                    balance = 0.0,
                ) }
            }
            is SourceEvent.SetBalance -> {
                _state.update { it.copy(
                    balance = event.balance
                ) }
            }
            is SourceEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is SourceEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is SourceEvent.SetType -> {
                _state.update {
                    it.copy(
                        sourceCategory = event.type
                    )
                }
            }
            SourceEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingNewSource = true
                ) }
            }
            is SourceEvent.SortSource -> {
                _sortTypes.value = event.sortType
            }
            is SourceEvent.FilterSource -> {
                _category.value = event.category
            }
        }
    }
}