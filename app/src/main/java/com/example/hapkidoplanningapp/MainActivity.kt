package com.example.hapkidoplanningapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NavbarProvider {

    lateinit var bottomNavigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_HapkidoPlanningApp)

        setContentView(R.layout.activity_main)

        loadFragment(Login())

        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)!!
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(Home())
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
                else -> false
            }
        }

    }
    override fun getBottomNav(): BottomNavigationView {
        return bottomNavigation
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


}
