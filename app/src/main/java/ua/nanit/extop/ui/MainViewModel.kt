package ua.nanit.extop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.nanit.extop.monitoring.Monitoring
import ua.nanit.extop.monitoring.data.Rate
import java.util.concurrent.Executors

class MainViewModel(private val monitoring: Monitoring) : ViewModel() {

    companion object {
        const val PAGE_RATES = 0
        const val PAGE_SETTINGS = 1
    }

    private val executor = Executors.newSingleThreadExecutor()

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page

    private val _rates = MutableLiveData<List<Rate>>()
    val rates: LiveData<List<Rate>> get() = _rates

    init {
        openRatesPage()
        refreshRates()
    }

    fun openRatesPage() {
        if (_page.value != PAGE_RATES)
            _page.value = PAGE_RATES
    }

    fun openSettingsPage() {
        if (_page.value != PAGE_SETTINGS)
            _page.value = PAGE_SETTINGS
    }

    fun refreshRates() {
        executor.execute {
            val rates = monitoring.updateRates()

            viewModelScope.launch {
                _rates.value = rates
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }

}