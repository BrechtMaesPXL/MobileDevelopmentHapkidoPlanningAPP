    package com.example.hapkidoplanningapp

    import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.hapkidoplanningapp.db.LocalDataBase
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.PermissionAsker
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

    private const val CHANNEL_ID = "A1"
    private const val CHANNEL_NAME = "Default Channel"
    private const val CHANNEL_DESCRIPTION = "This is the default channel for notifications"


    class MainActivity : AppCompatActivity(), NavbarProvider, dbLocal, UserProvider {

        lateinit var bottomNavigation : BottomNavigationView
        companion object {
            private const val CURRENT_FRAGMENT_TAG = "current_fragment_tag"
            private const val MY_FRAGMENT_KEY = "myFragmentName"
        }
        lateinit var db : LocalDataBase
        var currentUser : User? = User("main", "main")
        private var currentFragment: Fragment? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            var permissionAsker = PermissionAsker(this)

            permissionAsker.askNotificationPermission()
            createNotificationChannel()
    //        setContentView(R.layout.activity_main)

            // Maak de database
            this.db = Room.databaseBuilder(
                applicationContext,
                LocalDataBase::class.java, "mudori-localdb"
            ).fallbackToDestructiveMigration().build()


            FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                .addOnCompleteListener { task: Task<Void?> ->
                    var msg = "Subscribed to all_users topic"
                    if (!task.isSuccessful) {
                        msg = "Subscription failed"
                    }
                    Log.d(TAG, msg!!)
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                }

            setTheme(R.style.Theme_HapkidoPlanningApp)

            setContentView(R.layout.activity_main)

            loadFragment(Login())

            bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)!!



            if (savedInstanceState != null) {
                currentFragment = supportFragmentManager.getFragment(savedInstanceState, MY_FRAGMENT_KEY)
            } else {
                currentFragment = Login()
                loadFragment(currentFragment!!)
            }


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


        fun loadFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }

        override fun getUser(): User? {
            return currentUser
        }

        override fun updateUser(user:User?) {

            currentUser = user
        }
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment != null && currentFragment.isAdded) {
                supportFragmentManager.putFragment(outState, MY_FRAGMENT_KEY, currentFragment)
            }
        }

        override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)
            if (savedInstanceState != null) {
                try {
                    val restoredFragment = supportFragmentManager.getFragment(savedInstanceState, MY_FRAGMENT_KEY)
                    if (restoredFragment != null) {
                        loadFragment(restoredFragment)
                    }
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }
        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = CHANNEL_DESCRIPTION
                }

                notificationManager.createNotificationChannel(channel)
            }
        }




    }
