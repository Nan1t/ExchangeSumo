package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate

interface Monitoring {

    companion object {
        const val TYPE_EXCHANGE_SUMO = 0
        const val TYPE_BEST_CHANGE = 1
    }

    fun storage(): MonitoringStorage

    fun setSearchDetails(curIn: String, curOut: String)

    fun getCurrencies(): List<Currency>

    fun updateRates(): List<Rate>

    fun getExchanger(id: String): Exchanger

}