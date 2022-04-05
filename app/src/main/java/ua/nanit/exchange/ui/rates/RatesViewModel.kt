package ua.nanit.exchange.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.nanit.exchange.RatesMonitor
import ua.nanit.exchange.data.RateInfo
import ua.nanit.exchange.data.parsed.Currency
import ua.nanit.exchange.data.parsed.RatesInfo
import ua.nanit.exchange.log.Logger

class RatesViewModel(private val monitor: RatesMonitor) : ViewModel() {

    private val ratesLiveDataMut = MutableLiveData<List<RateInfo>>()
    private val currenciesLiveDataMut = MutableLiveData<List<Currency>>()

    val ratesLiveData: LiveData<List<RateInfo>> = ratesLiveDataMut
    val currenciesLiveData: LiveData<List<Currency>> = currenciesLiveDataMut

    init {
        monitor.setOnUpdate(this::onRatesUpdate)
        monitor.start()
        Logger.info("View model init")
    }

    private fun onRatesUpdate(info: RatesInfo) {
        ratesLiveDataMut.value = info.buildInfo()
    }

    override fun onCleared() {
        monitor.stop()
        super.onCleared()
    }

}