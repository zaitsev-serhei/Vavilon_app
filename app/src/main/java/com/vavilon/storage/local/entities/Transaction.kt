package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
class Transaction {
    constructor()

    constructor(amount: Double, category: String, creationDate: Date) {
        this.amount = amount
        this.category = category
        this.transactionDate = creationDate
    }
    constructor(amount: Double, category: String, description: String, date: Date) {
        this.amount = amount
        this.category = category
        this.transactionDate = date
        this.description = description
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transactionId: Long = 0

    @ColumnInfo(name = "amount")
    var amount: Double = 0.0

    @ColumnInfo(name = "description", defaultValue = "")
    var description: String = ""

    @ColumnInfo(name = "creation_date")
    var transactionDate: Date = Date()

    @ColumnInfo(name = "isRepeatable")
    var isRepeatable: Boolean = false

    @ColumnInfo(name = "currency_id")
    var currencyId: Long = 0

    @ColumnInfo(name = "source_id")
    var sourceId: Long = 0

    @ColumnInfo(name = "schedule_id")
    var schedule_id: Long = 0

    @ColumnInfo(name = "category_name")
    var category: String = ""
}