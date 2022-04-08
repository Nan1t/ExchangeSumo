package ua.nanit.extop.storage

import android.content.Context
import ua.nanit.extop.monitoring.AppStorage
import ua.nanit.extop.monitoring.Monitoring

class AppPrefsStorage(ctx: Context) : AppStorage {

    private val prefs = ctx.getSharedPreferences("config", Context.MODE_PRIVATE)

    override fun getMonitoringType(): Int {
        return prefs.getInt("monitoring_type", Monitoring.TYPE_EXCHANGE_SUMO)
    }

    override fun changeMonitoringType(type: Int) {
        prefs.edit()
            .putInt("monitoring_type", type)
            .apply()
    }
}