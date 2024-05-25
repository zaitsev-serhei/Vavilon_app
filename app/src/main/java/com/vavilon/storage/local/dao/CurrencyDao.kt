package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.Currency
import java.util.Locale

@Dao
interface CurrencyDao {
    @Insert
    fun insert(currency: Currency)
    @Update
    fun update(currency: Currency)
    @Query("SELECT * FROM currencies")
    fun getAll(): List<Currency>
}