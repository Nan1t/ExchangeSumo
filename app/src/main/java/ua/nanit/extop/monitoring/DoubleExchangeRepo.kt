package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.DoubleExchange

interface DoubleExchangeRepo {

    fun provide(currencyIn: String, currencyOut: String): List<DoubleExchange>

}