package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_for_plan")
class SourceForPlan {
    @PrimaryKey(autoGenerate = true)
    var id:Long =0
    @ColumnInfo(name = "plan_id")
    var planId = 0
    @ColumnInfo(name = "source_id")
    var sourceId = 0
}