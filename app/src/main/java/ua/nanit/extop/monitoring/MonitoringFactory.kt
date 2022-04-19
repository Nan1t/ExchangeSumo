package ua.nanit.extop.monitoring

interface MonitoringFactory {

    fun getStorage(): MonitoringStorage

    fun getCurrenciesRepo(): CurrencyRepo

    fun getRatesRepo(): RatesRepo

    fun getDoubleExchangeRepo(): DoubleExchangeRepo

    fun getExchangerRepo(): ExchangerRepo

    fun getCalculator(): RateCalculator

}