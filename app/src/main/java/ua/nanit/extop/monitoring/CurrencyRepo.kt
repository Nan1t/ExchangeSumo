package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Currency

interface CurrencyRepo {

    fun provide(): List<Currency>

    fun getCurrency(id: String): Currency?

}