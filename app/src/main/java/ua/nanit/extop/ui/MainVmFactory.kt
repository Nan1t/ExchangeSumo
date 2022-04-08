package ua.nanit.extop.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Monitoring
import ua.nanit.extop.monitoring.exsumo.ExSumoMonitoring
import ua.nanit.extop.storage.AppPrefsStorage
import ua.nanit.extop.storage.PrefsStorageFactory
import java.lang.IllegalArgumentException

class MainVmFactory(private val ctx: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val storage = AppPrefsStorage(ctx)
        val monitoring = createMonitoring(storage.getMonitoringType())
        return MainViewModel(monitoring) as T
    }

    private fun createMonitoring(type: Int): Monitoring {
        return when(type) {
            Monitoring.TYPE_EXCHANGE_SUMO -> {
                val currenciesStream = ctx.resources.openRawResource(R.raw.currencies_exsumo)
                val factory = PrefsStorageFactory(ctx, "exsumo")
                ExSumoMonitoring(factory, currenciesStream)
            }
            Monitoring.TYPE_BEST_CHANGE -> {
                null as Monitoring
            }
            else -> throw IllegalArgumentException("Invalid monitoring type id")
        }
    }

}