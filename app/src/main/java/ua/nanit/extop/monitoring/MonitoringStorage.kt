package ua.nanit.extop.monitoring

interface MonitoringStorage {

    fun getCurrencyIn(): String?

    fun getCurrencyOut(): String?

    fun getAmountIn(): Int

    fun getAmountOut(): Int

    fun isCalcCommissions(): Boolean

    fun saveCurrencies(curIn: String, curOut: String)

    interface Factory {
        fun create(): MonitoringStorage
    }

}