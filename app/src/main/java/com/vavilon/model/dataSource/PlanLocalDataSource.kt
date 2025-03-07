package com.vavilon.model.dataSource

import com.vavilon.model.dataHandlers.Plan
import kotlinx.coroutines.flow.Flow

interface PlanLocalDataSource {
    suspend fun createPlan(plan:Plan)
    suspend fun updatePlan(plan:Plan)
    fun getAllPlans(): Flow<List<Plan>>
}