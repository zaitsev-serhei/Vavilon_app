package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exchange_rates")
class ExchangeRatesEntity {
    constructor()
    constructor(
        baseCode: String,
        baseLabel: String,
        relatedCode: String,
        relatedLabel: String,
        rate: Double,
        updateDate: Date
    ) {
        this.code = baseCode
        this.label = baseLabel
        this.toCurrencyCode = relatedCode
        this.toCurrencyLabel = relatedLabel
        this.exchangeRate = rate
        this.lastUpdateDate = updateDate
    }

    constructor(baseCode: String, rate: Double, updateDate: Date) {
        this.code = baseCode
        this.exchangeRate = rate
        this.lastUpdateDate = updateDate
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var currencyId: Long = 0

    @ColumnInfo(name = "base_currency")
    var code: String = ""

    @ColumnInfo(name = "base_label")
    var label: String = ""

    @ColumnInfo(name = "to_currency")
    var toCurrencyCode: String = ""

    @ColumnInfo(name = "to_label")
    var toCurrencyLabel: String = ""

    @ColumnInfo(name = "rate")
    var exchangeRate: Double = 0.0

    @ColumnInfo(name = "last_update")
    var lastUpdateDate: Date = Date()

    @ColumnInfo(name = "isDefault")
    var isDefault: Boolean = false
}