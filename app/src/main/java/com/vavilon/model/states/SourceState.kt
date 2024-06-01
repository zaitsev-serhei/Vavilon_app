package com.vavilon.model.states

import com.vavilon.model.SortTypes
import com.vavilon.model.SourceCategories
import com.vavilon.storage.local.entities.Source

data class SourceState(
    val sourceList: List<Source> = emptyList(),
    val name : String = "",
    var sourceCategory: SourceCategories = SourceCategories.INCOME,
    val description:String = "",
    val balance: Double = 0.0,
    val isAddingNewSource: Boolean = false,
    val isEditingSource: Boolean = false,
    val sortTypes: SortTypes = SortTypes.ASC,
    )
