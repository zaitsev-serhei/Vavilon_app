package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.Plan
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Insert
    suspend fun insert(plan: Plan)
    @Update
    suspend fun update(plan:Plan)

    @Query("SELECT * FROM `plan` WHERE id= :planId")
    fun getPlan(planId:Long): Flow<Plan>

    @Query("SELECT * FROM `plan` WHERE status='INPROCESS'")
    fun getCurrentPlan():Flow<Plan>

    @Query("SELECT * FROM `plan` ")
    fun getAllPlans():Flow<List<Plan>>
    // TODO: add the table to DB and create a migration for  new table. Start working on PlanViewModel
}