package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.Plan
import com.vavilon.storage.local.entities.Transaction
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
    @Query("SELECT * FROM transactions")
    fun getPlanItems():Flow<List<Transaction>>
    // TODO: function to search items for current plan -- need to find the way to indicate items for current Plan
    /* SELECT * FROM transactions as tr
     left join transaction_for_plan as trInPlan on trInPlan.transaction_id = tr.transaction_id
 */
}