package ua.nanit.extop.monitoring.exsumo

import ua.nanit.extop.monitoring.data.Currency

/**
 * This "parser" reads predefined static currencies
 * because ExchangeSumo defines them statically in JS file
 */
class ExCurrencyParser {

    fun parse(): List<Currency> {
        return emptyList()
    }

}