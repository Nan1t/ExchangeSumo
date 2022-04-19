package ua.nanit.extop.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.RateCalculator
import ua.nanit.extop.monitoring.RatesRepo
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.Signal

class RatesViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val ratesRepo: RatesRepo,
    private var calculator: RateCalculator
) : ViewModel() {

    private val _error = Signal<String>()
    private val _rates = MutableLiveData<List<Rate>>()
    private val _currencies = MutableLiveData<String>()
    private val _emptyRates = MutableLiveData<Unit>()

    val error: LiveData<String> get() = _error
    val rates: LiveData<List<Rate>> get() = _rates
    val currencies: LiveData<String> get() = _currencies
    val emptyRates: LiveData<Unit> get() = _emptyRates

    init {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)
        }
    }

    fun refreshRates() {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)

            viewModelScope.launch(dispatcher) {
                loadRates(currencyIn, currencyOut)
            }
        } else {
            _rates.value = emptyList()
        }
    }

    fun calculate(amount: Float, dir: Direction) {
        val rates = _rates.value ?: return

        viewModelScope.launch(dispatcher) {
            calculator.calculate(rates, dir, amount)

            viewModelScope.launch {
                _rates.value = _rates.value
            }
        }
    }

    private fun loadRates(currencyIn: String, currencyOut: String) {
        var error: String? = null

        val rates: List<Rate> = try {
            ratesRepo.provide(currencyIn, currencyOut)
        } catch (th: Throwable) {
            th.printStackTrace()
            error = th.message
            emptyList()
        }

        if (error != null) {
            viewModelScope.launch { _error.setValue(error!!) }
            return
        }

        if (rates.isEmpty()) {
            viewModelScope.launch { _emptyRates.value = Unit }
        } else {
            viewModelScope.launch { _rates.value = rates }
        }
    }

    private fun postCurrencies(currencyIn: String, currencyOut: String) {
        _currencies.value = "$currencyIn - $currencyOut"
    }

}