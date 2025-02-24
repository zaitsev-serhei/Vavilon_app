package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.CurrencyEntity

@Dao
interface CurrencyDao {
    @Insert
    fun insert(currencyEntity: CurrencyEntity)
    @Update
    fun update(currencyEntity: CurrencyEntity)
    @Query("SELECT * FROM currencies")
    fun getAll(): List<CurrencyEntity>
}