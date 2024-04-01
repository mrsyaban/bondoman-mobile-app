package com.pbd.psi.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

enum class Category {
    INCOME,
    EXPENSE
}
class Converters {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @TypeConverter
    fun toDate(value: String?): Date? {
        return value?.let { dateFormat.parse(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    // Convert String to Category
    @TypeConverter
    fun toCategory(value: String?): Category? {
        return value?.let { Category.valueOf(it) }
    }

    // Convert Category to String
    @TypeConverter
    fun fromCategory(category: Category?): String? {
        return category?.name
    }
}
