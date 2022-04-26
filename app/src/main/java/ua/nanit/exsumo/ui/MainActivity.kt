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
import ua.nanit.exsumo.App
import ua.nanit.exsumo.R
import ua.nanit.exsumo.ui.dialog.TermsDialog
import ua.nanit.exsumo.util.LocaleUtil

class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storage = App.storage

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
        navController.addOnDestinationChangedListener(::onDestinationChanged)

        if (!storage.isTermsAccepted()) {
            TermsDialog().show(this, storage)
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

    override fun navigateUp() {
        navController.popBackStack()
    }

    private fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.nav_rates,
            R.id.nav_double_exchange,
            R.id.nav_settings -> {
                if (navView.visibility != View.VISIBLE)
                    navView.visibility = View.VISIBLE
            }
            else -> {
                if (navView.visibility != View.GONE)
                    navView.visibility = View.GONE
            }
        }
    }
}