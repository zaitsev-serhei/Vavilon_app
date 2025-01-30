package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vavilon.model.ItemStatus

@Entity(tableName = "plan")
class Plan {

    constructor(
        description: String,
        status: ItemStatus,
        creationDate: String,
        startDate: String,
        endDate: String,
    ) {
        this.description = description
        this.status = status
        this.creationDate = creationDate
        this.startDate = startDate
        this.endDate = endDate
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "status")
    var status: ItemStatus

    @ColumnInfo(name = "creation_date")
    var creationDate: String = ""

    @ColumnInfo(name = "start")
    var startDate: String = ""

    @ColumnInfo(name = "end")
    var endDate: String = ""

    @ColumnInfo(name = "tr_count")
    var transactionCount: Long = 0

    @ColumnInfo(name = "completed_tr_count")
    var completedTransactionCount: Long = 0

}