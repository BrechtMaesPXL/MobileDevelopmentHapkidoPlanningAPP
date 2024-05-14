package com.example.hapkidoplanningapp

import com.example.hapkidoplanningapp.db.LocalDataBase

interface dbLocal {
    fun getdb(): LocalDataBase

}