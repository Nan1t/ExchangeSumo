package ua.nanit.exsumo.ui.base

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import ua.nanit.exsumo.factory.ExchangeSumoFactory
import ua.nanit.exsumo.monitoring.MonitoringFactory

abstract class BaseVmFactory(
    private val ctx: Context
) : ViewModelProvider.Factory {

    protected fun getMonitoringFactory(): MonitoringFactory {
        return ExchangeSumoFactory(ctx)
    }

}