package com.vavilon.storage.local

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Converter {
    private val dateFormat = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: String?): Date? {
        return value?.let { dateFormat.parse(it) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }
}