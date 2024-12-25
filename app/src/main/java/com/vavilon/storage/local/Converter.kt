package com.vavilon.storage.local

import androidx.compose.ui.text.toUpperCase
import androidx.room.TypeConverter
import com.vavilon.model.ItemStatus
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

    @TypeConverter
    @JvmStatic
    fun fromItemStatus(status:ItemStatus?):String? {
        return status?.getItemStatus()
    }
    @TypeConverter
    @JvmStatic
    fun stringToItemStatus(status: String?): ItemStatus? {
        return status?.uppercase(Locale.getDefault())?.let {
            try {
                ItemStatus.valueOf(it)
            } catch (e: IllegalArgumentException) {
                ItemStatus.PLANNED
            }
        } ?: ItemStatus.PLANNED
    }
}