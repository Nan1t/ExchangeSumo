package ua.nanit.extop.factory

import android.content.Context
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.*
import ua.nanit.extop.monitoring.exsumo.SumoCurrencyRepo
import ua.nanit.extop.monitoring.exsumo.SumoExchangerRepo
import ua.nanit.extop.monitoring.exsumo.SumoRatesRepo
import ua.nanit.extop.storage.PrefsMonitoringStorage

class ExSumoFactory(
    private val ctx: Context
) : MonitoringFactory {

    override fun createStorage(): MonitoringStorage {
        return PrefsMonitoringStorage(ctx, "exsumo")
    }

    override fun createCurrenciesProvider(): CurrencyRepo {
        ctx.resources.openRawResource(R.raw.currencies_exsumo).use {
            return SumoCurrencyRepo(it)
        }
    }

    override fun createRatesProvider(): RatesRepo {
        return SumoRatesRepo()
    }

    override fun createExchangerProvider(): ExchangerRepo {
        return SumoExchangerRepo()
    }
}