package com.example.hapkidoplanningapp

import com.example.hapkidoplanningapp.domain.Activities

interface RVUListener {
    fun showToast(message: String){

    }
    fun updateRV()

    fun switchDetailframe(activatie: Activities)
}