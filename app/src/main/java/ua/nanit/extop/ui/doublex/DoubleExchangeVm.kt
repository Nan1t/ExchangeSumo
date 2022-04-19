package ua.nanit.extop.ui.doublex

import kotlinx.coroutines.CoroutineDispatcher
import ua.nanit.extop.monitoring.DoubleExchangeRepo
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.data.DoubleExchange
import ua.nanit.extop.ui.BaseRatesVm

class DoubleExchangeVm(
    dispatcher: CoroutineDispatcher,
    storage: MonitoringStorage,
    private val doubleExchangeRepo: DoubleExchangeRepo
) : BaseRatesVm<DoubleExchange>(dispatcher, storage) {

    override fun loadRates(currencyIn: String, currencyOut: String): List<DoubleExchange> {
        return doubleExchangeRepo.provide(currencyIn, currencyOut)
    }
}