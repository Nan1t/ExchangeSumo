package ua.nanit.extop.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nanit.extop.RatesMonitor
import ua.nanit.extop.currency.CurrencyProvider
import ua.nanit.extop.data.RateInfo
import ua.nanit.extop.data.parsed.Currency
import ua.nanit.extop.data.parsed.RatesInfo

class RatesViewModel(monitor: RatesMonitor, currencyProvider: CurrencyProvider) : ViewModel() {

    private val ratesLiveDataMut = MutableLiveData<List<RateInfo>>()
    private val currenciesLiveDataMut = MutableLiveData<List<Currency>>()

    val ratesLiveData: LiveData<List<RateInfo>> = ratesLiveDataMut
    val currenciesLiveData: LiveData<List<Currency>> = currenciesLiveDataMut

    init {
        monitor.setOnUpdate(this::onRatesUpdate)
        monitor.update()

        viewModelScope.launch {
            val currencies = withContext(Dispatchers.Default) {
                currencyProvider.getCurrencies()
            }

            currenciesLiveDataMut.value = currencies
        }
    }

    private fun onRatesUpdate(info: RatesInfo) {
        ratesLiveDataMut.value = info.buildInfo()
    }

}