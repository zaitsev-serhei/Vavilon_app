package com.vavilon.storage.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long = 0

    @ColumnInfo(name = "login")
    var login: String = ""

    @ColumnInfo(name = "password")
    var password: String = ""

    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "settings_id")
    var settingsId: Long = 0
}