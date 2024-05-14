package com.example.hapkidoplanningapp.converters

import androidx.room.TypeConverter
import com.example.hapkidoplanningapp.domain.User
import com.google.gson.Gson

class UserConverter {
    private val gson = Gson()

    @TypeConverter
    fun userToString(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun stringToUser(value: String): User {
        return gson.fromJson(value, User::class.java)
    }
}