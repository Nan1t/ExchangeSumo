package ua.nanit.exchange

import android.app.Application
import ua.nanit.exchange.log.AndroidLogger
import ua.nanit.exchange.log.Logger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init(AndroidLogger())
    }

}