package ua.nanit.exsumo.storage

import android.content.Context
import androidx.preference.PreferenceManager

class AppStorage(ctx: Context) {

    companion object {
        const val KEY_LOCALE = "locale"
        const val KEY_NIGHT_MODE = "night_mode"
        const val KEY_TERMS_ACCEPTED = "terms_accepted"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)

    fun locale(): String {
        return prefs.getString(KEY_LOCALE, "ru")!!
    }

    fun isNightMode(): Boolean {
        return prefs.getBoolean(KEY_NIGHT_MODE, false)
    }

    fun isTermsAccepted(): Boolean {
        return prefs.getBoolean(KEY_TERMS_ACCEPTED, false)
    }

    fun acceptTerms() {
        return prefs.edit()
            .putBoolean(KEY_TERMS_ACCEPTED, true)
            .apply()
    }
}