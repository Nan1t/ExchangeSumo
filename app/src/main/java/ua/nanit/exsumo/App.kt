package ua.nanit.exsumo

import android.app.Application
import android.content.Context
import ua.nanit.exsumo.log.AndroidLogger
import ua.nanit.exsumo.log.Logger
import ua.nanit.exsumo.storage.AppStorage
import ua.nanit.exsumo.util.ThemeUtil

class App : Application() {

    companion object {
        lateinit var storage: AppStorage
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        if (base != null) {
            storage = AppStorage(base)
            ThemeUtil.setNightMode(storage.isNightMode())
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.init(AndroidLogger())
    }

}