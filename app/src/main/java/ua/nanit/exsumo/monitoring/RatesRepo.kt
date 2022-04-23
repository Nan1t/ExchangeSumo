package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.Rate

interface RatesRepo {

    fun provide(currencyIn: String, currencyOut: String): List<Rate>

}