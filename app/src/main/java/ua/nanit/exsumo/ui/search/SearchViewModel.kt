package ua.nanit.exsumo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.exsumo.monitoring.CurrencyRepo
import ua.nanit.exsumo.monitoring.Direction
import ua.nanit.exsumo.monitoring.MonitoringStorage
import ua.nanit.exsumo.monitoring.data.Currency
import ua.nanit.exsumo.ui.Signal

class SearchViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val currencyRepo: CurrencyRepo
) : ViewModel() {

    private var currenciesMenuMode: Direction? = null

    private val _currencyIn = MutableLiveData<Currency>()
    private val _currencyOut = MutableLiveData<Currency>()
    private val _currencies = MutableLiveData<List<Currency>>()
    private val _enableConfirmBtn = MutableLiveData<Boolean>()
    private val _search = Signal<Unit>()

    val currencyIn: LiveData<Currency> get() = _currencyIn
    val currencyOut: LiveData<Currency> get() = _currencyOut
    val currencies: LiveData<List<Currency>> get() = _currencies
    val enableConfirmBtn: LiveData<Boolean> get() = _enableConfirmBtn
    val search: LiveData<Unit> get() = _search

    init {
        val savedCurrencyIn = storage.getCurrencyIn()
        val savedCurrencyOut = storage.getCurrencyOut()

        _enableConfirmBtn.value = false

        if (savedCurrencyIn != null && savedCurrencyOut != null) {
            val currencyIn = currencyRepo.getCurrency(savedCurrencyIn)
            val currencyOut = currencyRepo.getCurrency(savedCurrencyOut)

            if (currencyIn != null && currencyOut != null) {
                _currencyIn.value = currencyIn!!
                _currencyOut.value = currencyOut!!
                _enableConfirmBtn.value = true
            }
        }
    }

    fun applySearchParams() {
        val currencyIn = currencyIn.value?.id
        val currencyOut = currencyOut.value?.id

        if (currencyIn == null || currencyOut == null) {
            return
        }

        storage.setCurrencies(currencyIn, currencyOut)
        _search.setValue(Unit)
    }

    fun loadCurrencies(type: Direction) {
        currenciesMenuMode = type

        viewModelScope.launch(dispatcher) {
            val currencies = currencyRepo.provide()

            viewModelScope.launch {
                _currencies.value = currencies
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        when (currenciesMenuMode) {
            Direction.IN -> {
                _currencyIn.value = currency
            }
            Direction.OUT -> {
                _currencyOut.value = currency
            }
            else -> {}
        }

        _enableConfirmBtn.value = currencyIn.value != null
                && currencyOut.value != null
    }

    fun swapCurrencies() {
        val valIn = currencyIn.value
        val valOut = currencyOut.value

        if (valIn != null && valOut != null) {
            _currencyIn.value = valOut!!
            _currencyOut.value = valIn!!
        }
    }
}