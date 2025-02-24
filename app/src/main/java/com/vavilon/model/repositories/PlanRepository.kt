package com.vavilon.model.repositories

import com.vavilon.model.dataHandlers.PlanItemWrapper
import com.vavilon.storage.local.dao.PlanDao
import com.vavilon.storage.local.dao.SourceDao
import com.vavilon.storage.local.dao.TransactionDao
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap

@Singleton
class PlanRepository @Inject constructor(
    private val planDao: PlanDao,
    private val transactionDao: TransactionDao,
    private val sourceDao: SourceDao
) {
    private val planList = planDao.getAllPlans()
    private val plannedItemsForPlan: List<PlanItemWrapper> = emptyList()
    fun getPlanList() = planList

    suspend fun createPlan(plan: PlanEntity) {
        withContext(Dispatchers.IO) {
            planDao.insert(plan)
        }
    }

    suspend fun updatePlan(plan: PlanEntity) {
        withContext(Dispatchers.IO) {
            planDao.update(plan)
        }
    }

    fun getTransactionsForPlan(planId: Long): Flow<List<TransactionEntity>> {
        return planDao.getPlanTransactionList(planId)
    }

    suspend fun addSourceToPlan(planId: Long, sourceId: Long) {
        withContext(Dispatchers.IO) {
            planDao.addSourceToPlan(planId, sourceId)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getPlannedItems(planId: Long): Flow<Map<SourceEntity, List<TransactionEntity>>> {
        return planDao.getPlanSourceList(planId).flatMapLatest { sourceList ->
            planDao.getPlanTransactionList(planId).map { transactionList ->
                sourceList.associateWith { source ->
                    transactionList.filter { transaction -> transaction.sourceId == source.sourceId }
                }
            }
        }
    }

    private fun toItemWrapper(
        source: SourceEntity,
        transaction: TransactionEntity,
        planId: Long
    ): PlanItemWrapper {
        return PlanItemWrapper(source, transaction, planId)
    }

    private fun fromItemWrapper(item: PlanItemWrapper): Map<SourceEntity, TransactionEntity> {
        val mappedItem = HashMap<SourceEntity, TransactionEntity>()
        mappedItem.put(item.getSource(), item.getTransaction())
        return mappedItem
    }
}