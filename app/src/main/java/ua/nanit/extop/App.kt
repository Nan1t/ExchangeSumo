package ua.nanit.extop

import android.app.Application
import ua.nanit.extop.log.AndroidLogger
import ua.nanit.extop.log.Logger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init(AndroidLogger())
        AsyncUtil.init()
    }

}