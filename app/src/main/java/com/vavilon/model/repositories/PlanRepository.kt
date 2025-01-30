package com.vavilon.model.repositories

import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.Plan
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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

    fun getPlanList() = planList

    suspend fun createPlan(plan: Plan) {
        withContext(Dispatchers.IO) {
            planDao.insert(plan)
        }
    }

    suspend fun updatePlan(plan: Plan) {
        withContext(Dispatchers.IO) {
            planDao.update(plan)
        }
    }

    fun getTransactionsForPlan(planId: Long): Flow<List<Transaction>> {
        return planDao.getPlanTransactionList(planId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getPlannedSourceItems(planId: Long): Flow<Map<Source, List<Transaction>>> {
        return planDao.getPlanSourceList(planId).flatMapLatest { sourceList ->
            planDao.getPlanTransactionList(planId).map { transactionList ->
                sourceList.associateWith { source ->
                    transactionList.filter { transaction -> transaction.sourceId == source.sourceId }
                }
            }
        }
    }
}