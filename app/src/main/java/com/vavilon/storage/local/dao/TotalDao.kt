package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.vavilon.storage.local.entities.TotalBalance

@Dao
interface TotalDao {
    @Insert
    fun insert(total: TotalBalance)
    @Update
    fun update(total: TotalBalance)
}