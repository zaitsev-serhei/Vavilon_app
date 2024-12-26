package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.Plan
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanRepository @Inject constructor(
    private val planDao: PlanDao,
    private val transactionDao: TransactionDao,
    private val sourceDao: SourceDao
) {
    private val planList = planDao.getAllPlans()
    private val currentPlan = planDao.getCurrentPlan()
    private val planItems:Map<Source,List<Transaction>> = HashMap()
    fun getPlan() = planItems

    suspend fun initializePlanItems() {
        if (currentPlan.firstOrNull()!=null){
            var sourceList = currentPlan.first()
        }

    }
    suspend fun createPlan(plan:Plan){
        withContext(Dispatchers.IO) {
            planDao.insert(plan)
        }
    }
    suspend fun updatePlan(plan:Plan){
        withContext(Dispatchers.IO) {
            planDao.update(plan)
        }
    }

}