package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Insert
    suspend fun insert(plan: PlanEntity)

    @Update
    suspend fun update(plan: PlanEntity)

    @Query("SELECT * FROM `plan` WHERE id= :planId")
    fun getPlan(planId: Long): Flow<PlanEntity>

    @Query("SELECT * FROM `plan` ")
    fun getAllPlans(): Flow<List<PlanEntity>>

    // TODO: add the table to DB and create a migration for  new table. Start working on PlanViewModel
    @Query(
        "SELECT t.* FROM transactions as t " +
                "INNER JOIN transaction_for_plan as tfp ON tfp.transaction_id = t.transaction_id " +
                "WHERE tfp.plan_id =:planId"
    )
    fun getPlanTransactionList(planId: Long): Flow<List<TransactionEntity>>

    @Query(
        "Select * FROM sources as s " +
                "INNER JOIN source_for_plan as sfp ON sfp.source_id = s.source_id " +
                "WHERE sfp.plan_id = :planId"
    )
    fun getPlanSourceList(planId: Long): Flow<List<SourceEntity>>

    @Query("INSERT INTO source_for_plan (plan_id,source_id) VALUES (:planId,:sourceId)")
    fun addSourceToPlan(planId: Long, sourceId: Long)
}