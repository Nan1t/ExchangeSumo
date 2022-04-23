package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.Currency

interface CurrencyRepo {

    fun provide(): List<Currency>

    fun getCurrency(id: String): Currency?

}