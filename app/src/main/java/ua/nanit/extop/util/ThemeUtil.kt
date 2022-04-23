package ua.nanit.extop.util

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {

    fun setNightMode(nightMode: Boolean) {
        val mode = if (nightMode)
            AppCompatDelegate.MODE_NIGHT_YES
        else
            AppCompatDelegate.MODE_NIGHT_NO

        AppCompatDelegate.setDefaultNightMode(mode)
    }

}