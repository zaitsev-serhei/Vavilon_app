package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "totals")
class TotalBalance {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "total_id")
    var totalId: Long = 0

    @ColumnInfo(name = "source_id")
    var sourceId: Long = 0

    @ColumnInfo(name = "value")
    var value = 0.0
}