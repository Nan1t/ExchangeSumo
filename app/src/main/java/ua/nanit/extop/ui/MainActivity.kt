package ua.nanit.extop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.storage.AppStorage
import ua.nanit.extop.util.LocaleUtil

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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun navToSearch() {
        // TODO change to action
        navController.navigate(R.id.nav_search)
    }

    override fun navToCurrencies() {
        navController.navigate(R.id.action_nav_search_to_nav_currencies)
    }

    override fun navToExchanger() {
        navController.navigate(R.id.action_nav_rates_to_nav_exchanger)
    }

    override fun navigateUp() {
        navController.popBackStack()
    }

    override fun show() {
        navView.visibility = View.VISIBLE
    }

    override fun hide() {
        navView.visibility = View.GONE
    }
}