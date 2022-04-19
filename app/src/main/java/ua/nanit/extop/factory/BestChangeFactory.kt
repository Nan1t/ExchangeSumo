package ua.nanit.extop.factory

import ua.nanit.extop.monitoring.*

class BestChangeFactory : MonitoringFactory {

    override fun getStorage(): MonitoringStorage {
        TODO("Not yet implemented")
    }

    override fun getCurrenciesRepo(): CurrencyRepo {
        TODO("Not yet implemented")
    }

    override fun getRatesRepo(): RatesRepo {
        TODO("Not yet implemented")
    }

    override fun getDoubleExchangeRepo(): DoubleExchangeRepo {
        TODO("Not yet implemented")
    }

    override fun getExchangerRepo(): ExchangerRepo {
        TODO("Not yet implemented")
    }

    override fun getCalculator(): RateCalculator {
        TODO("Not yet implemented")
    }
}