package ua.nanit.extop.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.CurrencyRepo
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.ui.Signal

class SearchViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val currencyRepo: CurrencyRepo
) : ViewModel() {

    private var currenciesMenuMode: Direction? = null

    private val _currencyIn: MutableLiveData<Currency> = MutableLiveData()
    private val _currencyOut: MutableLiveData<Currency> = MutableLiveData()
    private val _currencies: MutableLiveData<List<Currency>> = MutableLiveData()
    private val _applyState: Signal<ApplyState> = Signal()

    val currencyIn: LiveData<Currency> get() = _currencyIn
    val currencyOut: LiveData<Currency> get() = _currencyOut
    val currencies: LiveData<List<Currency>> get() = _currencies
    val applyState: LiveData<ApplyState> get() = _applyState

    init {
        val savedCurrencyIn = storage.getCurrencyIn()
        val savedCurrencyOut = storage.getCurrencyOut()

        if (savedCurrencyIn != null && savedCurrencyOut != null) {
            val currencyIn = currencyRepo.getCurrency(savedCurrencyIn)
            val currencyOut = currencyRepo.getCurrency(savedCurrencyOut)

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
            _applyState.setValue(ApplyState.NO_CURRENCY_IN)
            return
        }

        if (currencyOut == null) {
            _applyState.setValue(ApplyState.NO_CURRENCY_OUT)
            return
        }

        storage.setCurrencies(currencyIn, currencyOut)
        _applyState.setValue(ApplyState.SUCCESS)
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
        when(currenciesMenuMode) {
            Direction.IN -> {
                _currencyIn.value = currency
            }
            Direction.OUT -> {
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