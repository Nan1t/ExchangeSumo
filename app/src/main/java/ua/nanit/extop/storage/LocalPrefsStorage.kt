package ua.nanit.extop.storage

import android.content.SharedPreferences
import ua.nanit.extop.monitoring.LocalStorage

class LocalPrefsStorage(private val prefs: SharedPreferences) : LocalStorage {

    companion object {
        private const val KEY_CURRENCY_IN = "currency_in"
        private const val KEY_CURRENCY_OUT = "currency_out"
        private const val KEY_AMOUNT_IN = "amount_in"
        private const val KEY_AMOUNT_OUT = "amount_out"
        private const val KEY_CALC_COMMISSIONS = "calc_commissions"
    }

    override fun getCurrencyIn(): String? {
        return prefs.getString(KEY_CURRENCY_IN, null)
    }

    override fun getCurrencyOut(): String? {
        return prefs.getString(KEY_CURRENCY_OUT, null)
    }

    override fun getAmountIn(): Int {
        return prefs.getInt(KEY_AMOUNT_IN, 0)
    }

    override fun getAmountOut(): Int {
        return prefs.getInt(KEY_AMOUNT_OUT, 0)
    }

    override fun isCalcCommissions(): Boolean {
        return prefs.getBoolean(KEY_CALC_COMMISSIONS, false)
    }

    override fun saveCurrencies(curIn: String, curOut: String) {
        prefs.edit()
            .putString(KEY_CURRENCY_IN, curIn)
            .putString(KEY_CURRENCY_OUT, curOut)
            .apply()
    }
}