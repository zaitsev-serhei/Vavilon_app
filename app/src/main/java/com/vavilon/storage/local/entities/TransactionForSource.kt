package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_for_source")
class TransactionForSource {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
    @ColumnInfo(name = "source_id")
    var sourceId:Long =0
    @ColumnInfo(name = "transaction_id")
    var transactionId:Long =0
}