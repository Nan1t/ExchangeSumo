package ua.nanit.extop.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.MonitoringStorage
import ua.nanit.extop.monitoring.RateCalculator
import ua.nanit.extop.monitoring.RatesRepo
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BaseRatesVm

class RatesViewModel(
    private val dispatcher: CoroutineDispatcher,
    storage: MonitoringStorage,
    private val ratesRepo: RatesRepo,
    private var calculator: RateCalculator
) : BaseRatesVm<Rate>(dispatcher, storage) {

    private val _currencies = MutableLiveData<String>()

    val currencies: LiveData<String> get() = _currencies

    init {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            postCurrencies(currencyIn, currencyOut)
        }
    }

    override fun loadRates(currencyIn: String, currencyOut: String): List<Rate> {
        return ratesRepo.provide(currencyIn, currencyOut)
    }

    fun calculate(amount: Double, dir: Direction) {
        val rates = _rates.value ?: return

        viewModelScope.launch(dispatcher) {
            calculator.calculate(rates, dir, amount)

            viewModelScope.launch {
                _rates.value = _rates.value
            }
        }
    }

    private fun postCurrencies(currencyIn: String, currencyOut: String) {
        _currencies.value = "$currencyIn - $currencyOut"
    }

}