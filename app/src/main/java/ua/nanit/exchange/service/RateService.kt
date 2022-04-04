package ua.nanit.exchange.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import ua.nanit.exchange.PREFS_CURRENCY_FROM
import ua.nanit.exchange.PREFS_CURRENCY_TO
import ua.nanit.exchange.Resources
import ua.nanit.exchange.data.RatesInfo
import ua.nanit.exchange.event.CurrencyChangedEvent
import ua.nanit.exchange.event.RatesUpdatedEvent
import ua.nanit.exchange.log.Logger
import ua.nanit.exchange.network.RatesMonitor

class RateService : Service() {

    private lateinit var monitor: RatesMonitor
    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        prefs = Resources.getPrefs(this)
        val from = prefs.getString(PREFS_CURRENCY_FROM, null)
        val to = prefs.getString(PREFS_CURRENCY_FROM, null)

        monitor = RatesMonitor()
        monitor.updateCurrency(from, to)
        monitor.setOnUpdate(this::onUpdate)
        monitor.start()

        EventBus.getDefault().register(this)
        Logger.info("Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        monitor.stop()
        EventBus.getDefault().unregister(this)
        Logger.info("Service destroyed")
    }

    @Subscribe
    private fun onCurrencyChanged(event: CurrencyChangedEvent) {
        prefs.edit()
            .putString(PREFS_CURRENCY_FROM, event.from)
            .putString(PREFS_CURRENCY_TO, event.to)
            .apply()

        monitor.updateCurrency(event.from, event.to)
    }

    private fun onUpdate(info: RatesInfo) {
        EventBus.getDefault().post(RatesUpdatedEvent(info))
    }

    override fun onBind(intent: Intent?): IBinder? = null

}