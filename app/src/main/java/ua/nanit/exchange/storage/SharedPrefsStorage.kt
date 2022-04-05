package ua.nanit.exchange.storage

import android.content.SharedPreferences
import ua.nanit.exchange.data.SearchOptions

class SharedPrefsStorage(private val prefs: SharedPreferences) : RatesStorage {

    override fun getCurrencyIn(): String? {
        return prefs.getString("currency_in", null)
    }

    override fun getCurrencyOut(): String? {
        return prefs.getString("currency_out", null)
    }

    override fun getAmountIn(): Int {
        return prefs.getInt("amount_in", 0)
    }

    override fun getAmountOut(): Int {
        return prefs.getInt("amount_out", 0)
    }

    override fun isCalcCommissions(): Boolean {
        return prefs.getBoolean("calc_commissions", false)
    }

    override fun saveOptions(options: SearchOptions) {
        prefs.edit()
            .putString("currency_in", options.currencyIn)
            .putString("currency_out", options.currencyOut)
            .putInt("amount_in", options.amountIn)
            .putInt("amount_out", options.amountOut)
            .putBoolean("calc_commissions", options.calcCommissions)
            .apply()
    }
}