package ua.nanit.exsumo.monitoring

interface MonitoringFactory {

    fun getStorage(): MonitoringStorage

    fun getCurrenciesRepo(): CurrencyRepo

    fun getRatesRepo(): RatesRepo

    fun getDoubleExchangeRepo(): DoubleRatesRepo

    fun getExchangerRepo(): ExchangerRepo

    fun getCalculator(): RateCalculator

}