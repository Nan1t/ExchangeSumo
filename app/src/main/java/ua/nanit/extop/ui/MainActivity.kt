package ua.nanit.extop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.ui.currencies.CurrenciesFragment
import ua.nanit.extop.ui.pages.DoubleExchangeFragment
import ua.nanit.extop.ui.pages.ExchangersFragment
import ua.nanit.extop.ui.pages.SearchFragment
import ua.nanit.extop.ui.pages.SettingsFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val BACKSTACK_MAIN = "main"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.main_navbar)

        nav.setOnItemSelectedListener(this::onMenuItemClicked)

        viewModel = ViewModelProvider(this, MainVmFactory(this))
            .get(MainViewModel::class.java)

        viewModel.page.observe(this, this::observePage)
        viewModel.currenciesMenu.observe(this, this::observeCurrenciesMenu)
    }

    private fun observeCurrenciesMenu(ignored: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.main_container, CurrenciesFragment())
            .addToBackStack(BACKSTACK_MAIN)
            .commit()

        Logger.info("Opened currencies menu")
    }

    private fun observePage(index: Int) {
        when(index) {
            MainViewModel.PAGE_SEARCH ->
                openPage(SearchFragment())
            MainViewModel.PAGE_EXCHANGERS ->
                openPage(ExchangersFragment())
            MainViewModel.PAGE_DOUBLE_EXCHANGE ->
                openPage(DoubleExchangeFragment())
            MainViewModel.PAGE_SETTINGS ->
                openPage(SettingsFragment())
        }
    }

    private fun onMenuItemClicked(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_search -> {
                viewModel.openSearchPage()
                true
            }
            R.id.nav_exchangers -> {
                viewModel.openExchangersPage()
                true
            }
            R.id.nav_double_exchange -> {
                viewModel.openDoubleExchangePage()
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
        supportFragmentManager.popBackStack(BACKSTACK_MAIN,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.main_container, page)
            .commit()
    }
}