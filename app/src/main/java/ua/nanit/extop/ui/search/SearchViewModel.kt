package ua.nanit.extop.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.nanit.extop.AsyncUtil
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.CurrencyProvider
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.data.Currency

class SearchViewModel(
    private val storage: MonitoringStorage,
    private val currencyProvider: CurrencyProvider
) : ViewModel() {

    companion object {
        const val CURRENCIES_NONE = 0
        const val CURRENCIES_IN = 1
        const val CURRENCIES_OUT = 2
    }

    init {
        Logger.info("Search VM created")
    }

    private val executor = AsyncUtil.executor()

    private val _currencyIn: MutableLiveData<Currency> = MutableLiveData()
    val currencyIn: LiveData<Currency> get() = _currencyIn

    private val _currencyOut: MutableLiveData<Currency> = MutableLiveData()
    val currencyOut: LiveData<Currency> get() = _currencyOut

    private val _currencies: MutableLiveData<List<Currency>> = MutableLiveData()
    val currencies: LiveData<List<Currency>> get() = _currencies

    private var currenciesMenuMode = CURRENCIES_NONE

    fun loadCurrencies(mode: Int) {
        currenciesMenuMode = mode

        executor.execute {
            val currencies = currencyProvider.provide()
            viewModelScope.launch { _currencies.value = currencies }
        }
    }

    fun selectCurrency(currency: Currency) {
        when(currenciesMenuMode) {
            CURRENCIES_IN -> {
                _currencyIn.value = currency
            }
            CURRENCIES_OUT -> {
                _currencyOut.value = currency
            }
        }
    }

    fun swapCurrencies() {
        val valIn = _currencyIn.value
        val valOut = _currencyOut.value

        if (valIn != null && valOut != null) {
            _currencyIn.value = valOut!!
            _currencyOut.value = valIn!!
        }
    }

    override fun onCleared() {
        super.onCleared()
        Logger.info("Search VM destroyed")
    }
}