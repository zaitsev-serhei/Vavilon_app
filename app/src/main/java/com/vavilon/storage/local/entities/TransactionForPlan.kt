package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_for_plan")
class TransactionForPlan {
    @PrimaryKey(autoGenerate = false)
    var id:Long =0
    @ColumnInfo(name = "plan_id")
    var planId: Long = 0
    @ColumnInfo(name = "transaction_id")
    var transactionId:Long = 0
}