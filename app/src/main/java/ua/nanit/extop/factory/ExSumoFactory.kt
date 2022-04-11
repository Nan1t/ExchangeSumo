package ua.nanit.extop.factory

import android.content.Context
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.*
import ua.nanit.extop.monitoring.exsumo.ExCurrencyProvider
import ua.nanit.extop.monitoring.exsumo.ExExchangerProvider
import ua.nanit.extop.monitoring.exsumo.ExRatesProvider
import ua.nanit.extop.storage.PrefsMonitoringStorage

class ExSumoFactory(
    private val ctx: Context
) : MonitoringFactory {

    override fun createStorage(): MonitoringStorage {
        return PrefsMonitoringStorage(ctx, "exsumo")
    }

    override fun createCurrenciesProvider(): CurrencyProvider {
        ctx.resources.openRawResource(R.raw.currencies_exsumo).use {
            return ExCurrencyProvider(it)
        }
    }

    override fun createRatesProvider(): RatesProvider {
        return ExRatesProvider()
    }

    override fun createExchangerProvider(): ExchangerProvider {
        return ExExchangerProvider()
    }
}