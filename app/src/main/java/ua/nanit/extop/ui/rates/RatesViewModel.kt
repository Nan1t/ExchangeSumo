package ua.nanit.extop.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.RatesProvider
import ua.nanit.extop.monitoring.data.Rate

class RatesViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val ratesProvider: RatesProvider
) : ViewModel() {

    private val _rates: MutableLiveData<List<Rate>> = MutableLiveData()
    private val _currencies: MutableLiveData<String> = MutableLiveData()

    val rates: LiveData<List<Rate>> get() = _rates
    val currencies: LiveData<String> get() = _currencies

    init {
        nonNullCurrencies { currencyIn, currencyOut ->
            postCurrencies(currencyIn, currencyOut)
        }
    }

    fun refreshRates() {
        nonNullCurrencies { currencyIn, currencyOut ->
            postCurrencies(currencyIn, currencyOut)

            viewModelScope.launch(dispatcher) {
                val rates = ratesProvider.provide(currencyIn, currencyOut)
                viewModelScope.launch { _rates.value = rates }
            }
        }
    }

    private fun nonNullCurrencies(callback: (curIn: String, curOut: String) -> Unit) {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            callback(currencyIn, currencyOut)
        }
    }

    private fun postCurrencies(currencyIn: String, currencyOut: String) {
        _currencies.value = "$currencyIn - $currencyOut"
    }

}