package ua.nanit.extop

import android.app.Application
import android.content.Context
import ua.nanit.extop.log.AndroidLogger
import ua.nanit.extop.log.Logger
import ua.nanit.extop.storage.AppStorage
import ua.nanit.extop.util.ThemeUtil

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        if (base != null) {
            val storage = AppStorage(base)
            ThemeUtil.setNightMode(storage.nightMode())
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.init(AndroidLogger())
    }

}