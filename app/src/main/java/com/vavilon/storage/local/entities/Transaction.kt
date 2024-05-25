package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
class Transaction {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transactionId: Long = 0

    @ColumnInfo(name = "amount")
    var amount = 0.0

    @ColumnInfo(name = "creation_date")
    var transactionDate: Date? = null

    @ColumnInfo(name = "isRepeatable")
    var isRepeatable = false

    @ColumnInfo(name = "currency_id")
    var currencyId: Long = 0

    @ColumnInfo(name = "source_id")
    var sourceId: Long = 0

    @ColumnInfo(name = "schedule_id")
    var schedule_id: Long = 0

    @ColumnInfo(name = "category_id")
    var categoryId: Long = 0
}