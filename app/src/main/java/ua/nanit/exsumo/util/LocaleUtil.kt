package ua.nanit.exsumo.util

import android.content.Context
import android.os.Build
import java.util.*

object LocaleUtil {

    fun updateLocale(ctx: Context, localeId: String) {
        val res = ctx.resources
        val config = res.configuration
        val locale = Locale(localeId)

        Locale.setDefault(locale)
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            ctx.createConfigurationContext(config)

        res.updateConfiguration(config, res.displayMetrics)
    }

}