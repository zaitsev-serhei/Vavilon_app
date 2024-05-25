package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
class Currency {
    @ColumnInfo(name = "currency_id")
    @PrimaryKey(autoGenerate = true)
    var currencyId: Long = 0
    @ColumnInfo(name = "code")
    var code: String = ""
    @field:ColumnInfo(name = "label")
    var label: String = ""
    @field:ColumnInfo(name = "rate")
    var exchangeRate: Double = 0.0
    @field:ColumnInfo(name = "isDefault")
    var isDefault: Boolean = false
}