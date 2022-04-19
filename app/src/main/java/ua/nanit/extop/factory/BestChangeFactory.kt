package ua.nanit.extop.factory

import ua.nanit.extop.monitoring.*

class BestChangeFactory : MonitoringFactory {

    override fun createStorage(): MonitoringStorage {
        TODO("Not yet implemented")
    }

    override fun createCurrenciesProvider(): CurrencyRepo {
        TODO("Not yet implemented")
    }

    override fun createRatesProvider(): RatesRepo {
        TODO("Not yet implemented")
    }

    override fun createExchangerProvider(): ExchangerRepo {
        TODO("Not yet implemented")
    }

    override fun createCalculator(): RateCalculator {
        TODO("Not yet implemented")
    }
}