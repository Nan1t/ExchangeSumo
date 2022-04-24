package ua.nanit.exsumo.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.exsumo.monitoring.*
import ua.nanit.exsumo.monitoring.data.Computed
import ua.nanit.exsumo.monitoring.data.DoubleRate
import ua.nanit.exsumo.monitoring.data.Rate

class RatesViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage,
    private val ratesRepo: RatesRepo,
    private var doubleRatesRepo: DoubleRatesRepo,
    private var calculator: RateCalculator
) : ViewModel() {

    private val _error = Signal<String>()
    private val _rates = MutableLiveData<List<Rate>>()
    private val _doubleRates = MutableLiveData<List<DoubleRate>>()
    private val _currencies = MutableLiveData<String>()

    val error: LiveData<String> get() = _error
    val rates: LiveData<List<Rate>> get() = _rates
    val doubleRates: LiveData<List<DoubleRate>> get() = _doubleRates
    val currencies: LiveData<String> get() = _currencies

    fun refreshRates() {
        refresh(ratesRepo::provide, _rates)
    }

    fun refreshDoubleRates() {
        refresh(doubleRatesRepo::provide, _doubleRates)
    }

    fun calculateRates(amount: Double, dir: Direction) {
        val rates = rates.value ?: return
        calculate(amount, dir, rates, _rates)
    }

    fun calculateDoubleRates(amount: Double, dir: Direction) {
        val rates = doubleRates.value ?: return
        calculate(amount, dir, rates, _doubleRates)
    }

    private fun <T> refresh(
        provider: (String, String) -> List<T>,
        livedata: MutableLiveData<List<T>>
    ) {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)

            viewModelScope.launch(dispatcher) {
                load(currencyIn, currencyOut, provider, livedata)
            }

            return
        }

        livedata.value = emptyList()
    }

    @SuppressLint("NullSafeMutableLiveData")
    private fun <T> load(
        currencyIn: String,
        currencyOut: String,
        provider: (String, String) -> List<T>,
        livedata: MutableLiveData<List<T>>
    ) {
        var error: String? = null

        val rates: List<T> = try {
            provider(currencyIn, currencyOut)
        } catch (th: Throwable) {
            error = th.message
            emptyList()
        }

        if (error != null) {
            viewModelScope.launch { _error.setValue(error) }
            return
        }

        viewModelScope.launch { livedata.value = rates }
    }

    private fun <T: Computed> calculate(
        amount: Double,
        dir: Direction,
        list: List<T>,
        livedata: MutableLiveData<List<T>>
    ) {
        viewModelScope.launch(dispatcher) {
            calculator.calculate(list, dir, amount)

            viewModelScope.launch {
                livedata.value = livedata.value
            }
        }
    }

    private fun postCurrencies(currencyIn: String, currencyOut: String) {
        _currencies.value = "$currencyIn - $currencyOut"
    }
}