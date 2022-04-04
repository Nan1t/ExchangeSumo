package ua.nanit.exchange.log

import android.util.Log

class AndroidLogger : LogAdapter {

    private val tag = "ExchangeRates"

    override fun debug(msg: String) {
        Log.d(tag, msg)
    }

    override fun info(msg: String) {
        Log.i(tag, msg)
    }

    override fun warn(msg: String) {
        Log.w(tag, msg)
    }

    override fun error(msg: String) {
        Log.e(tag, msg)
    }
}