package com.example.shoppingapp.database

import androidx.room.TypeConverter
import com.example.shoppingapp.entity.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(name: String): Category {
        return Category(
            creationAt = "",
            id = 0,
            image = "",
            name = name ?: "",
            updatedAt = ""
        )
    }

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return Gson().toJson(list)
    }
}