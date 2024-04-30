package com.example.hapkidoplanningapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_HapkidoPlanningApp)

        setContentView(R.layout.activity_main)

        loadFragment(Login())

        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)!!
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(fragment_activaties())
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_manu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val visible = when (supportFragmentManager.findFragmentById(R.id.container)) {
            is fragment_add_activetie -> false
            else -> true
        }
        menu?.setGroupVisible(R.id.manu_group , visible)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}