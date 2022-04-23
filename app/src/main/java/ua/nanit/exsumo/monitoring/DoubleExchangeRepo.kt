package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.DoubleRate

interface DoubleExchangeRepo {

    fun provide(currencyIn: String, currencyOut: String): List<DoubleRate>

}