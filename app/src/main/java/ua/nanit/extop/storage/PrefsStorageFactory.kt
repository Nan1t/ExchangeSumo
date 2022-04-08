package ua.nanit.extop.storage

import android.content.Context
import ua.nanit.extop.monitoring.MonitoringStorage

class PrefsStorageFactory(
    private val ctx: Context,
    private val name: String
) : MonitoringStorage.Factory {

    override fun create(): MonitoringStorage {
        val prefs = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)
        return MonitoringPrefsStorage(prefs)
    }

}