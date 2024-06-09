package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tr_categories")
class TransactionCategory {
    constructor()
    constructor(transactionTitle: String, transactionType: String) {
        this.title = transactionTitle
        this.type = transactionType
    }

    constructor(transactionTitle: String, transactionDescription: String, transactionType: String) {
        this.title = transactionTitle
        this.description = transactionDescription
        this.type = transactionType
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var categoryId: Long = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description", defaultValue = "")
    var description: String = ""

    @ColumnInfo(name = "type", defaultValue = "")
    var type: String = ""
}