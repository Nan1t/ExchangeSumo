package ua.nanit.extop.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.nanit.extop.AsyncUtil
import ua.nanit.extop.monitoring.CurrencyProvider
import ua.nanit.extop.monitoring.CurrencyType
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.data.Currency

class SearchViewModel(
    private val storage: MonitoringStorage,
    private val currencyProvider: CurrencyProvider
) : ViewModel() {

    companion object {
        const val RESULT_SUCCESS = 0
        const val RESULT_MISSING_CURRENCY_IN = 1
        const val RESULT_MISSING_CURRENCY_OUT = 2
    }

    private val executor = AsyncUtil.executor()
    private var currenciesMenuMode: CurrencyType? = null

    private val _currencyIn: MutableLiveData<Currency> = MutableLiveData()
    val currencyIn: LiveData<Currency> get() = _currencyIn

    private val _currencyOut: MutableLiveData<Currency> = MutableLiveData()
    val currencyOut: LiveData<Currency> get() = _currencyOut

    private val _currencies: MutableLiveData<List<Currency>> = MutableLiveData()
    val currencies: LiveData<List<Currency>> get() = _currencies

    private val _applyParamsCallback: MutableLiveData<Int> = MutableLiveData()
    val applyParamsCallback: LiveData<Int?> get() = _applyParamsCallback

    init {
        val savedCurrencyIn = storage.getCurrencyIn()
        val savedCurrencyOut = storage.getCurrencyOut()

        if (savedCurrencyIn != null && savedCurrencyOut != null) {
            val currencyIn = currencyProvider.getCurrency(savedCurrencyIn)
            val currencyOut = currencyProvider.getCurrency(savedCurrencyOut)

            if (currencyIn != null && currencyOut != null) {
                _currencyIn.value = currencyIn!!
                _currencyOut.value = currencyOut!!
            }
        }
    }

    fun applySearchParams() {
        val currencyIn = _currencyIn.value?.id
        val currencyOut = _currencyOut.value?.id

        if (currencyIn == null) {
            _applyParamsCallback.value = RESULT_MISSING_CURRENCY_IN
            return
        }

        if (currencyOut == null) {
            _applyParamsCallback.value = RESULT_MISSING_CURRENCY_OUT
            return
        }

        storage.setCurrencies(currencyIn, currencyOut)
        _applyParamsCallback.value = RESULT_SUCCESS
    }

    fun loadCurrencies(type: CurrencyType) {
        currenciesMenuMode = type

        executor.execute {
            val currencies = currencyProvider.provide()
            viewModelScope.launch { _currencies.value = currencies }
        }
    }

    fun selectCurrency(currency: Currency) {
        when(currenciesMenuMode) {
            CurrencyType.IN -> {
                _currencyIn.value = currency
            }
            CurrencyType.OUT -> {
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
}