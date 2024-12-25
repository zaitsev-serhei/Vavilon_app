package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vavilon.model.ItemStatus

@Entity(tableName = "transactions")
class Transaction {
    constructor()

    constructor(amount: Double, category: String, creationDate: String, status:ItemStatus) {
        this.amount = amount
        this.category = category
        this.transactionDate = creationDate
        this.status = status
    }

    constructor(
        amount: Double,
        category: String,
        status: ItemStatus,
        description: String,
        creationDate: String,
    ) {
        this.amount = amount
        this.category = category
        this.description = description
        this.status = status
        this.transactionDate = creationDate
    }

    constructor(
        amount: Double,
        category: String,
        description: String,
        status: ItemStatus,
        date: String,
    ) {
        this.amount = amount
        this.category = category
        this.status = status
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

    @ColumnInfo(name = "status")
    var status: ItemStatus = ItemStatus.PLANNED

    @ColumnInfo(name = "creation_date")
    var transactionDate: String = ""

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