package ua.nanit.extop.monitoring

interface MonitoringFactory {

    fun createStorage(): MonitoringStorage

    fun createCurrenciesProvider(): CurrencyRepo

    fun createRatesProvider(): RatesRepo

    fun createExchangerProvider(): ExchangerRepo

    fun createCalculator(): RateCalculator

}