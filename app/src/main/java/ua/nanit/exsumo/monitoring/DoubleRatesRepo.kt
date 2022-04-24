package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.DoubleRate

interface DoubleRatesRepo {

    fun provide(currencyIn: String, currencyOut: String): List<DoubleRate>

}