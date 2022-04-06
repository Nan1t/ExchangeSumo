package ua.nanit.extop.monitoring.exsumo

import ua.nanit.extop.monitoring.LocalStorage
import ua.nanit.extop.monitoring.Monitoring
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate

class ExSumoMonitoring(storageFactory: LocalStorage.Factory) : Monitoring {

    private val storage = storageFactory.create()

    override fun storage(): LocalStorage = storage

    override fun setSearchDetails(curIn: String, curOut: String) {
        storage.saveCurrencies(curIn, curOut)
    }

    override fun getCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }

    override fun updateRates(): List<Rate> {
        TODO("Not yet implemented")
    }

    override fun getExchanger(id: String): Exchanger {
        TODO("Not yet implemented")
    }
}