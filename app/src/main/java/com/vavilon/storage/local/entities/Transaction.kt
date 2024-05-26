package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
class Transaction {
    constructor()

    constructor(amount:Double, categoryId: Long, creationDate: Date ) {
        this.amount = amount
        this.categoryId = categoryId
        this.transactionDate = creationDate
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transactionId: Long = 0

    @ColumnInfo(name = "amount")
    var amount :Double = 0.0

    @ColumnInfo(name = "description")
    var description:String = ""

    @ColumnInfo(name = "creation_date")
    var transactionDate: Date? = null

    @ColumnInfo(name = "isRepeatable")
    var isRepeatable :Boolean= false

    @ColumnInfo(name = "currency_id")
    var currencyId: Long = 0

    @ColumnInfo(name = "source_id")
    var sourceId: Long = 0

    @ColumnInfo(name = "schedule_id")
    var schedule_id: Long = 0

    @ColumnInfo(name = "category_id")
    var categoryId: Long = 0
}