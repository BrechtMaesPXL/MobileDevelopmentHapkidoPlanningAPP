package com.example.hapkidoplanningapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.domain.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setTheme(R.style.Theme_HapkidoPlanningApp) // Add this line
        setContentView(R.layout.activity_main)
        loadFragment(ActivatieFragment())
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)!!
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(ActivatieFragment())
                    true
                }
                R.id.message -> {
                    loadFragment(LocationFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(UserFragment())
                    true
                }
                else -> false // Toegevoegd else tak om de 'when' expressie volledig te maken
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}

