package ua.nanit.exchange.ui.rates

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.nanit.exchange.RatesMonitor
import ua.nanit.exchange.Resources
import ua.nanit.exchange.storage.SharedPrefsStorage

class RatesVmFactory(private val ctx: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val prefs = Resources.getPrefs(ctx)
        val storage = SharedPrefsStorage(prefs)
        val monitor = RatesMonitor(storage)
        return RatesViewModel(monitor) as T
    }

}