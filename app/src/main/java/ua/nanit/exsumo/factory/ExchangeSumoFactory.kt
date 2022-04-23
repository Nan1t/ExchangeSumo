package ua.nanit.exsumo.factory

import android.content.Context
import ua.nanit.exsumo.R
import ua.nanit.exsumo.monitoring.*
import ua.nanit.exsumo.monitoring.impl.SumoCurrencyRepo
import ua.nanit.exsumo.monitoring.impl.SumoDoubleExchangeRepo
import ua.nanit.exsumo.monitoring.impl.SumoExchangerRepo
import ua.nanit.exsumo.monitoring.impl.SumoRatesRepo
import ua.nanit.exsumo.storage.PrefsMonitoringStorage

class ExchangeSumoFactory(
    private val ctx: Context
) : MonitoringFactory {

    override fun getStorage(): MonitoringStorage {
        return PrefsMonitoringStorage(ctx, "exsumo")
    }

    override fun getCurrenciesRepo(): CurrencyRepo {
        ctx.resources.openRawResource(R.raw.currencies_exsumo).use {
            return SumoCurrencyRepo(it)
        }
    }

    override fun getRatesRepo(): RatesRepo {
        return SumoRatesRepo()
    }

    override fun getExchangerRepo(): ExchangerRepo {
        return SumoExchangerRepo()
    }

    override fun getDoubleExchangeRepo(): DoubleExchangeRepo {
        return SumoDoubleExchangeRepo()
    }

    override fun getCalculator(): RateCalculator {
        return CommonCalculator()
    }
}