package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan")
class Plan {
    constructor()
    constructor(description: String, creationDate: String, startDate: String, endDate: String) {
        this.description = description
        this.creationDate = creationDate
        this.startDate = startDate
        this.endtDate = endDate
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "source_id")
    var source_id: Long = 0

    @ColumnInfo(name = "transaction_id")
    var transactionId: Long = 0

    @ColumnInfo(name = "creation_date")
    var creationDate: String = ""

    @ColumnInfo(name = "start")
    var startDate: String = ""

    @ColumnInfo(name = "end")
    var endtDate: String = ""
}