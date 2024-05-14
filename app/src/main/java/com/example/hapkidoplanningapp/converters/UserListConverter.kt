package com.example.hapkidoplanningapp.converters

import androidx.room.TypeConverter
import com.example.hapkidoplanningapp.domain.User
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class UserListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromUserList(userList: List<User>?): String {
        return gson.toJson(userList)
    }

    @TypeConverter
    fun toUserList(data: String): List<User>? {
        val listType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(data, listType)
    }
}