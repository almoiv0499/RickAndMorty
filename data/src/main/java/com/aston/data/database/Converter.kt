package com.aston.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val typeToken = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, typeToken)
    }

    @TypeConverter
    fun toListOfString(values: List<String>): String = Gson().toJson(values)
}