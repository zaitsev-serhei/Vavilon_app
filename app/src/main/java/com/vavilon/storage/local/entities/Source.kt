package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "sources")
class Source {
    constructor()
    constructor(
        sourceType: String,
        sourceTitle: String,
        sourceDescription: String,
        balance: Double
    ) {
        this.sourceType = sourceType
        this.sourceTitle = sourceTitle
        this.sourceDescription = sourceDescription
        this.currentBalance = balance
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "source_id")
    var sourceId: Long = 0

    @ColumnInfo(name = "type")
    var sourceType: String = ""

    @ColumnInfo(name = "title")
    var sourceTitle: String = ""

    @ColumnInfo("icon_id")
    var iconId: Int? = null

    @ColumnInfo(name = "description")
    var sourceDescription: String =""

    @ColumnInfo(name = "current_balance")
    var currentBalance: Double = 0.0

    @ColumnInfo(name = "total_balance")
    var totalBalance = 0.0

    @ColumnInfo(name = "currency_id")
    var currencyId: Long = 0

    @ColumnInfo(name = "isRepeatable")
    var isRepeatable = false

    @ColumnInfo(name = "creation_date")
    var creationDate: String = ""

    @ColumnInfo(name = "schedule_id")
    var scheduleId: Long = 0

    @ColumnInfo(name = "isDeleted")
    var isDeleted = false

    @Ignore
    override fun toString(): String {
        return "Source(sourceId=$sourceId, sourceType='$sourceType',\n" +
                " sourceTitle='$sourceTitle', sourceDescription='$sourceDescription',\n" +
                " currentBalance=$currentBalance)"
    }

}