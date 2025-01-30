package com.vavilon.model.states

import com.vavilon.model.ItemStatus
import com.vavilon.storage.local.entities.Plan
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction

data class PlanState(
    val planList: List<Plan> = emptyList(),
    val planedItemsMap: Map<Source, List<Transaction>> = emptyMap(),
    val currentPlan: Plan? = null,
    val isEditingPlan: Boolean = false,
    val sourceId: Long = 0,
    val transactionId: Long = 0,
    val itemStatus: ItemStatus = ItemStatus.PLANNED,
    val errorMessage:String? = ""
)