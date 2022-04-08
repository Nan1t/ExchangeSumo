package ua.nanit.extop.monitoring.exsumo

import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.Monitoring
import ua.nanit.extop.monitoring.data.Currencies
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate
import java.io.InputStream

class ExSumoMonitoring(
    storageFactory: MonitoringStorage.Factory,
    currenciesFile: InputStream
) : Monitoring {

    private val storage = storageFactory.create()
    private val currenciesParser = ExCurrencyParser(currenciesFile)
    private val ratesParser = ExRatesParser()

    private var currencies: Currencies? = null

    override fun storage(): MonitoringStorage = storage

    override fun setSearchDetails(curIn: String, curOut: String) {
        storage.saveCurrencies(curIn, curOut)
    }

    override fun getCurrencies(): Currencies {
        if (currencies == null) {
            currencies = currenciesParser.parse()
        }
        return currencies!!
    }

    override fun updateRates(): List<Rate> {
        val curIn = storage.getCurrencyIn()
        val curOut = storage.getCurrencyOut()

        return ratesParser.parse("QIWIRUR", "MONOBUAH")
    }

    override fun getExchanger(id: String): Exchanger {
        TODO("Not yet implemented")
    }
}