package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate

interface Monitoring {

    fun storage(): LocalStorage

    fun setSearchDetails(curIn: String, curOut: String)

    fun getCurrencies(): List<Currency>

    fun updateRates(): List<Rate>

    fun getExchanger(id: String): Exchanger

}