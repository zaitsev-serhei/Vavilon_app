package com.vavilon.model.states

import com.vavilon.model.SortType
import com.vavilon.model.SourceCategory
import com.vavilon.storage.local.entities.Source
import com.vavilon.utils.errors.ErrorHandler

data class SourceState(
    val sourceList: List<Source> = emptyList(),
    val name : String = "",
    var sourceCategory: SourceCategory = SourceCategory.INCOME,
    val description:String = "",
    val balance: Double = 0.0,
    val isAddingNewSource: Boolean = false,
    val sortType: SortType = SortType.ASC,
    )
