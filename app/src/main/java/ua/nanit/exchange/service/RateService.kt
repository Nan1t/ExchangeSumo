package ua.nanit.exchange.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ua.nanit.exchange.monitor.RatesMonitor

class RateService : Service() {

    private lateinit var monitor: RatesMonitor

    override fun onCreate() {
        super.onCreate()
        monitor = RatesMonitor()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

}