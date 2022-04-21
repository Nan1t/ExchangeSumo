package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.DoubleRate

interface DoubleExchangeRepo {

    fun provide(currencyIn: String, currencyOut: String): List<DoubleRate>

}