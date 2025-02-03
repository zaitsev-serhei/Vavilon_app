package com.vavilon.model.events

import com.vavilon.storage.local.entities.Plan

sealed interface PlanEvent {
    object SavePlan : PlanEvent
    object AddPlan : PlanEvent
    data class UpdatePlan(val plan: Plan) : PlanEvent
    data class AddSourceToPlan(val sourceId: Long) : PlanEvent
    data class SetTransactionId(val transactionId: Long) : PlanEvent
}