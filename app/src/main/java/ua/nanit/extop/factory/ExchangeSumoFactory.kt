package ua.nanit.extop.factory

import android.content.Context
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.*
import ua.nanit.extop.monitoring.exsumo.SumoCurrencyRepo
import ua.nanit.extop.monitoring.exsumo.SumoDoubleExchangeRepo
import ua.nanit.extop.monitoring.exsumo.SumoExchangerRepo
import ua.nanit.extop.monitoring.exsumo.SumoRatesRepo
import ua.nanit.extop.storage.PrefsMonitoringStorage

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