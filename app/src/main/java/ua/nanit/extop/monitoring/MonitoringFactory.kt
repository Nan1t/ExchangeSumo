package ua.nanit.extop.monitoring

interface MonitoringFactory {

    fun createStorage(): MonitoringStorage

    fun createCurrenciesProvider(): CurrencyProvider

    fun createRatesProvider(): RatesProvider

    fun createExchangerProvider(): ExchangerProvider

}