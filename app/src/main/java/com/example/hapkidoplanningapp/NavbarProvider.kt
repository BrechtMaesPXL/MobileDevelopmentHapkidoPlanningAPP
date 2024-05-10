package com.example.hapkidoplanningapp

import com.google.android.material.bottomnavigation.BottomNavigationView

interface NavbarProvider {
    fun getBottomNav(): BottomNavigationView
}