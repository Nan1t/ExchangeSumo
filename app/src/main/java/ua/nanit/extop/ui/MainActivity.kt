package ua.nanit.extop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.extop.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val nav = findViewById<BottomNavigationView>(R.id.main_navbar)
        val controller = findNavController(R.id.main_nav_controller)
        val configuration = AppBarConfiguration(setOf(
            R.id.nav_search,
            R.id.nav_rates,
            R.id.nav_double_exchange,
            R.id.nav_settings
        ))

        setupActionBarWithNavController(controller, configuration)
        nav.setupWithNavController(controller)
    }
}