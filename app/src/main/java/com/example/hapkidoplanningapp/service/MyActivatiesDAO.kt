package com.example.hapkidoplanningapp.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hapkidoplanningapp.domain.Activities

@Dao
interface MyActivatiesDAO {
    @Query("SELECT * FROM activatie")
    fun getAll(): List<Activities>
    @Insert
    fun insertAll(vararg activaties: Activities)
    @Delete
    fun delete(activaites: Activities)
}