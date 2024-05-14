    package com.example.hapkidoplanningapp

    import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.hapkidoplanningapp.db.LocalDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView

    class MainActivity : AppCompatActivity(), NavbarProvider, dbLocal {

        lateinit var bottomNavigation : BottomNavigationView

        lateinit var db : LocalDataBase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

    //        setContentView(R.layout.activity_main)

            // Maak de database
            this.db = Room.databaseBuilder(
                applicationContext,
                LocalDataBase::class.java, "mudori-localdb"
            ).fallbackToDestructiveMigration().build()






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
        override fun getdb() : LocalDataBase {
            return db
        }


        private fun loadFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }


    }
