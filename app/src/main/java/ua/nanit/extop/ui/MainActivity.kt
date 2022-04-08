package ua.nanit.extop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.extop.R
import ua.nanit.extop.ui.pages.CurrenciesFragment
import ua.nanit.extop.ui.pages.RatesFragment
import ua.nanit.extop.ui.pages.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.main_navbar)

        nav.setOnItemSelectedListener(this::onMenuItemClicked)

        viewModel = ViewModelProvider(this, MainVmFactory(this))
            .get(MainViewModel::class.java)

        viewModel.page.observe(this, this::observePage)
    }

    private fun observePage(index: Int) {
        when(index) {
            MainViewModel.PAGE_RATES ->
                openPage(RatesFragment())
            MainViewModel.PAGE_CURRENCIES ->
                openPage(CurrenciesFragment())
            MainViewModel.PAGE_SETTINGS ->
                openPage(SettingsFragment())
        }
    }

    private fun onMenuItemClicked(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_rates -> {
                viewModel.openRatesPage()
                true
            }
            R.id.nav_currencies -> {
                viewModel.openCurrenciesPage()
                true
            }
            R.id.nav_settings -> {
                viewModel.openSettingsPage()
                true
            }
            else -> false
        }
    }

    private fun openPage(page: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.main_container, page)
            .commit()
    }
}