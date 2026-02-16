package com.vavilon.model.states

import com.vavilon.model.ItemStatus
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity

data class PlanState(
    val planList: List<PlanEntity> = emptyList(),
    val planedItemsMap: Map<SourceEntity, List<TransactionEntity>> = emptyMap(),
    val currentPlan: PlanEntity = PlanEntity(),
    val isEditingPlan: Boolean = false,
    val sourceId: Long = 0,
    val transactionId: Long = 0,
    val itemStatus: ItemStatus = ItemStatus.PLANNED,
    val errorMessage:String? = ""
)