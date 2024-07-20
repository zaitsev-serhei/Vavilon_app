package com.vavilon.model.states

import com.vavilon.model.SortTypes
import com.vavilon.model.SourceCategories
import com.vavilon.storage.local.entities.Source

data class SourceState(
    val sourceList: List<Source> = emptyList(),
    val sourceId: Long = 0,
    val name: String = "",
    var sourceCategory: SourceCategories = SourceCategories.INCOME,
    val description: String = "",
    val balance: Double = 0.0,
    val isAddingNewSource: Boolean = false,
    val isEditingSource: Boolean = false,
    val sortTypes: SortTypes = SortTypes.ASC,
    val limitCount : Long = 5,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val totalSavings: Double = 0.0,
    val currentBalance: Double = 0.0
) {
    override fun toString(): String {
        return "SourceState(sourceId=$sourceId, name='$name',\n" +
                "sourceCategory=$sourceCategory, description='$description',\n" +
                "balance=$balance, isAddingNewSource=$isAddingNewSource, isEditingSource=$isEditingSource)"
    }
}
