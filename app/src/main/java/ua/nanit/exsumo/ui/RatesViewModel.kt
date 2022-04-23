package ua.nanit.exsumo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.exsumo.log.Logger
import ua.nanit.exsumo.monitoring.*
import ua.nanit.exsumo.monitoring.data.DoubleRate
import ua.nanit.exsumo.monitoring.data.Rate

class RatesViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val ratesRepo: RatesRepo,
    private var doubleExchangeRepo: DoubleExchangeRepo,
    private var calculator: RateCalculator
) : ViewModel() {

    private val _error = Signal<String>()
    private val _rates = MutableLiveData<List<Rate>>()
    private val _doubleRates = MutableLiveData<List<DoubleRate>>()
    private val _emptyRates = MutableLiveData<Unit>()
    private val _currencies = MutableLiveData<String>()

    val error: LiveData<String> get() = _error
    val rates: LiveData<List<Rate>> get() = _rates
    val doubleRates: LiveData<List<DoubleRate>> get() = _doubleRates
    val emptyRates: LiveData<Unit> get() = _emptyRates
    val currencies: LiveData<String> get() = _currencies

    init {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)
        }
    }

    fun refreshRates(restore: Boolean = false) {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)

            if (restore && rates.value != null) return

            viewModelScope.launch(dispatcher) {
                Logger.info("Load rates")
                loadRates(currencyIn, currencyOut)
            }
        } else {
            postEmpty()
        }
    }

    fun refreshDoubleRates(restore: Boolean = false) {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)

            if (restore && doubleRates.value != null) return

            viewModelScope.launch(dispatcher) {
                Logger.info("Load double rates")
                loadDoubleRates(currencyIn, currencyOut)
            }
        } else {
            postEmpty()
        }
    }

    private fun loadRates(currencyIn: String, currencyOut: String) {
        var error: String? = null

        val rates: List<Rate> = try {
            ratesRepo.provide(currencyIn, currencyOut)
        } catch (th: Throwable) {
            error = th.message
            emptyList()
        }

        if (error != null) {
            viewModelScope.launch { _error.setValue(error!!) }
            return
        }

        if (rates.isEmpty()) {
            viewModelScope.launch { postEmpty() }
        } else {
            viewModelScope.launch { _rates.value = rates }
        }
    }

    private fun loadDoubleRates(currencyIn: String, currencyOut: String) {
        var error: String? = null

        val rates: List<DoubleRate> = try {
            doubleExchangeRepo.provide(currencyIn, currencyOut)
        } catch (th: Throwable) {
            error = th.message
            emptyList()
        }

        if (error != null) {
            viewModelScope.launch { _error.setValue(error!!) }
            return
        }

        if (rates.isEmpty()) {
            viewModelScope.launch { postEmpty() }
        } else {
            viewModelScope.launch { _doubleRates.value = rates }
        }
    }

    fun calculateRates(amount: Double, dir: Direction) {
        val rates = rates.value ?: return

        viewModelScope.launch(dispatcher) {
            calculator.calculate(rates, dir, amount)

            viewModelScope.launch {
                _rates.value = _rates.value
            }
        }
    }

    fun calculateDoubleRates(amount: Double, dir: Direction) {
        val rates = doubleRates.value ?: return

        viewModelScope.launch(dispatcher) {
            calculator.calculate(rates, dir, amount)

            viewModelScope.launch {
                _doubleRates.value = _doubleRates.value
            }
        }
    }

    private fun postEmpty() {
        _emptyRates.value = Unit
    }

    private fun postCurrencies(currencyIn: String, currencyOut: String) {
        _currencies.value = "$currencyIn - $currencyOut"
    }
}