package ua.nanit.extop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.MonitoringStorage

abstract class BaseRatesVm<T>(
    private val dispatcher: CoroutineDispatcher,
    private val storage: MonitoringStorage
) : ViewModel() {

    private val _error = Signal<String>()
    protected val _rates = MutableLiveData<List<T>>()
    private val _emptyRates = MutableLiveData<Unit>()

    val error: LiveData<String> get() = _error
    val rates: LiveData<List<T>> get() = _rates
    val emptyRates: LiveData<Unit> get() = _emptyRates

    fun refresh() {
        val currencyIn = storage.getCurrencyIn()
        val currencyOut = storage.getCurrencyOut()

        if (currencyIn != null && currencyOut != null) {
            viewModelScope.launch(dispatcher) {
                load(currencyIn, currencyOut)
            }
        } else {
            postEmpty()
        }
    }

    abstract fun loadRates(currencyIn: String, currencyOut: String): List<T>

    private fun load(currencyIn: String, currencyOut: String) {
        var error: String? = null

        val rates: List<T> = try {
            loadRates(currencyIn, currencyOut)
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
            viewModelScope.launch { postEmpty() }
        } else {
            viewModelScope.launch { _rates.value = rates }
        }
    }

    private fun postEmpty() {
        _emptyRates.value = Unit
    }
}