package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vavilon.storage.local.entities.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert
    suspend fun insert(exchangeRatesEntity: ExchangeRatesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<ExchangeRatesEntity>)

    @Update
    fun update(exchangeRatesEntity: ExchangeRatesEntity)

    @Query("SELECT * FROM exchange_rates")
    fun getAll(): Flow<List<ExchangeRatesEntity>>

    @Query("DELETE FROM exchange_rates")
    suspend fun clearRates()
}