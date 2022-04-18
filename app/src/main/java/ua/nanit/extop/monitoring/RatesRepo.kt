package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Rate

interface RatesRepo {

    fun provide(currencyIn: String, currencyOut: String): List<Rate>

}