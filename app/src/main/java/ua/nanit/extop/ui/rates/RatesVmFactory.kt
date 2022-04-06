package ua.nanit.extop.ui.rates

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.RatesMonitor
import ua.nanit.extop.Resources
import ua.nanit.extop.currency.ParsedCurrencyProvider
import ua.nanit.extop.monitoring.storage.SharedPrefsStorage

class RatesVmFactory(private val ctx: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val prefs = Resources.getPrefs(ctx)
        val storage = SharedPrefsStorage(prefs)
        val monitor = RatesMonitor(storage)
        val currencyProvider = ParsedCurrencyProvider()

        return RatesViewModel(monitor, currencyProvider) as T
    }

}