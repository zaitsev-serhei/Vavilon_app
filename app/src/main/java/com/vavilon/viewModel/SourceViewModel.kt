package com.vavilon.viewModel

import android.util.Log
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
class SourceViewModel @Inject constructor(private val sourceRepository: SourceRepository) :
    ViewModel() {
    private val _sortTypes = MutableStateFlow(SortTypes.ASC)
    private val _category = MutableStateFlow(SourceCategories.INCOME)
    private val _sourceListSorted = _category
        .flatMapLatest { category ->
            when (category) {
                SourceCategories.INCOME -> sourceRepository.getSourceListAsc(category)
                SourceCategories.EXPENSE -> sourceRepository.getSourceListAsc(category)
                SourceCategories.SAVING -> sourceRepository.getSourceListAsc(category)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(SourceState())
    val state = combine(_state, _category, _sourceListSorted) { state, category, sourceList ->
        state.copy(
            sourceCategory = category,
            sourceList = sourceList,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SourceState())

    fun onEvent(event: SourceEvent) {
        when (event) {
            is SourceEvent.DeleteSource -> {
                viewModelScope.launch {
                    sourceRepository.deleteSource(event.source)
                }
            }

            SourceEvent.SaveSource -> {
                val id = state.value.sourceId
                val name = state.value.name
                val description = state.value.description
                val sourceType = state.value.sourceCategory
                val balance = state.value.balance
                if (name.isBlank() || description.isBlank()) {
                    return
                }
                viewModelScope.launch {
                    val source = sourceRepository.getSource(id).firstOrNull()
                    Log.d("Source to save", "Before Edit Source: ${source.toString()}")
                    if (source != null) {
                        source.sourceTitle = name
                        source.sourceDescription = description
                        source.currentBalance = balance
                        Log.d("Source to save", "After Edit Source: ${source.toString()}")
                        sourceRepository.updateSource(source)
                    } else {
                        val source = Source(sourceType.getSrcCategory(), name, description, balance)
                        Log.d("Source to save", "New Source: ${source.toString()}")
                        sourceRepository.createSource(source)
                    }
                }
                _state.update {
                    it.copy(
                        isAddingNewSource = false,
                        isEditingSource = false,
                        sourceId = 0,
                        name = "",
                        sourceCategory = SourceCategories.INCOME,
                        description = "",
                        balance = 0.0,
                    )
                }
            }

            is SourceEvent.SetBalance -> {
                _state.update {
                    it.copy(
                        balance = event.balance
                    )
                }
            }

            is SourceEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
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

            SourceEvent.AddSource -> {
                _state.update {
                    it.copy(
                        isAddingNewSource = true
                    )
                }
            }

            is SourceEvent.EditSource -> {
                _state.update {
                    it.copy(
                        sourceId = event.source.sourceId,
                        name = event.source.sourceTitle,
                        sourceCategory = SourceCategories.entries.firstOrNull { category ->
                            category.getSrcCategory() == event.source.sourceType
                        } ?: SourceCategories.INCOME,
                        description = event.source.sourceDescription,
                        balance = event.source.currentBalance,
                        isEditingSource = true
                    )
                }
                Log.d("Edit source", "Current state: ${state.toString()}")
            }

            SourceEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingNewSource = false,
                        isEditingSource = false
                    )
                }
            }

            is SourceEvent.SortSource -> {
                _sortTypes.value = event.sortTypes
            }

            is SourceEvent.FilterSource -> {
                _category.value = event.category
            }
        }
    }
}