package ua.nanit.extop

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ua.nanit.extop.log.AndroidLogger
import ua.nanit.extop.log.Logger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        Logger.init(AndroidLogger())
    }

}