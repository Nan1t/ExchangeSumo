package ua.nanit.extop.monitoring

interface MonitoringStorage {

    fun getCurrencyIn(): String?

    fun getCurrencyOut(): String?

    fun setCurrencies(currencyIn: String, currencyOut: String)

}