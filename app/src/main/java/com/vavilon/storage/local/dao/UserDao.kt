package com.vavilon.storage.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.vavilon.storage.local.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(bewUser: UserEntity)
    @Update
    fun update(user: UserEntity)
}