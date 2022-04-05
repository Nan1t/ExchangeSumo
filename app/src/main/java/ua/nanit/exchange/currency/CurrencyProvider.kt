package ua.nanit.exchange.currency

import ua.nanit.exchange.data.parsed.Currency

interface CurrencyProvider {

    fun getCurrencies(): List<Currency>

}