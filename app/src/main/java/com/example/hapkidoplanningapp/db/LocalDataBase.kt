package com.example.hapkidoplanningapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hapkidoplanningapp.converters.Converters
import com.example.hapkidoplanningapp.converters.UserConverter
import com.example.hapkidoplanningapp.converters.UserListConverter
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.MyActivatiesDAO

@Database(entities = [Activities::class], version = 1)
@TypeConverters(Converters::class, UserConverter::class, UserListConverter::class)
abstract class LocalDataBase : RoomDatabase () {
    abstract fun MyActivatiesDAO() : MyActivatiesDAO
}