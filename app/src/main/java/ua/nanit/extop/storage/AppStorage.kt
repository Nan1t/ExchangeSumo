package ua.nanit.extop.storage

import android.content.Context
import androidx.preference.PreferenceManager

class AppStorage(ctx: Context) {

    companion object {
        const val KEY_LOCALE = "locale"
        const val KEY_NIGHT_MODE = "night_mode"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)

    fun locale(): String {
        return prefs.getString(KEY_LOCALE, "ru")!!
    }

    fun nightMode(): Boolean {
        return prefs.getBoolean(KEY_NIGHT_MODE, false)
    }
}