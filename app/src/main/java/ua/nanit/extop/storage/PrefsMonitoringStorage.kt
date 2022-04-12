package ua.nanit.extop.storage

import android.content.Context
import ua.nanit.extop.monitoring.MonitoringStorage

class PrefsMonitoringStorage(ctx: Context, name: String) : MonitoringStorage {

    companion object {
        private const val KEY_CURRENCY_IN = "currency_in"
        private const val KEY_CURRENCY_OUT = "currency_out"
    }

    private val prefs = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun getCurrencyIn(): String? {
        return prefs.getString(KEY_CURRENCY_IN, null)
    }

    override fun getCurrencyOut(): String? {
        return prefs.getString(KEY_CURRENCY_OUT, null)
    }

    override fun setCurrencies(currencyIn: String, currencyOut: String) {
        prefs.edit()
            .putString(KEY_CURRENCY_IN, currencyIn)
            .putString(KEY_CURRENCY_OUT, currencyOut)
            .apply()
    }
}