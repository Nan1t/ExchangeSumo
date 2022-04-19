package ua.nanit.extop.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.factory.BestChangeFactory
import ua.nanit.extop.factory.ExchangeSumoFactory
import ua.nanit.extop.monitoring.MonitoringFactory
import ua.nanit.extop.monitoring.MonitoringType

abstract class BaseVmFactory(
    private val ctx: Context
) : ViewModelProvider.Factory {

    protected fun getMonitoringFactory(): MonitoringFactory {
        val prefs = ctx.getSharedPreferences("config", Context.MODE_PRIVATE)
        val typeId = prefs.getInt("monitoring_type", MonitoringType.EXCHANGE_SUMO.typeId)

        return when (MonitoringType.byId(typeId)) {
            MonitoringType.EXCHANGE_SUMO -> {
                ExchangeSumoFactory(ctx)
            }
            MonitoringType.BEST_CHANGE -> {
                BestChangeFactory()
            }
        }
    }

}