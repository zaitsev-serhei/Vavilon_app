package com.vavilon.model.events

import com.vavilon.model.SortTypes
import com.vavilon.model.SourceCategories
import com.vavilon.storage.local.entities.Source

sealed interface SourceEvent {
    object SaveSource: SourceEvent
    object ShowDialog: SourceEvent
    object HideDialog: SourceEvent
    data class SetName(val name:String) : SourceEvent
    data class SetType(val type: SourceCategories) : SourceEvent
    data class SetDescription(val description:String) : SourceEvent
    data class SetBalance(val balance:Double) : SourceEvent
    data class DeleteSource(val source: Source): SourceEvent
    data class SortSource(val sortType: SortTypes): SourceEvent
    data class FilterSource(val category:SourceCategories): SourceEvent
}