package ua.nanit.exsumo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.exsumo.R
import ua.nanit.exsumo.storage.AppStorage
import ua.nanit.exsumo.util.LocaleUtil

class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storage = AppStorage(this)
        LocaleUtil.updateLocale(this, storage.locale())
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0.0F

        navController = findNavController(R.id.main_nav_controller)
        navView = findViewById(R.id.main_navbar)
        val configuration = AppBarConfiguration(setOf(
            R.id.nav_rates,
            R.id.nav_double_exchange,
            R.id.nav_settings
        ))

        setupActionBarWithNavController(navController, configuration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.nav_rates,
                R.id.nav_double_exchange,
                R.id.nav_settings -> {
                    navView.visibility = View.VISIBLE
                }
                else -> navView.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun navToSearch() {
        navController.navigate(R.id.action_nav_search)
    }

    override fun navToCurrencies() {
        navController.navigate(R.id.action_nav_currencies)
    }

    override fun navToExchanger() {
        navController.navigate(R.id.action_nav_exchanger)
    }

    override fun navToAbout() {
        navController.navigate(R.id.action_nav_about)
    }

    override fun navToTermsAndConditions() {
        navController.navigate(R.id.action_nav_tos)
    }

    override fun navigateUp() {
        navController.popBackStack()
    }
}