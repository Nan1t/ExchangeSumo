package ua.nanit.exsumo.ui.exchanger

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ua.nanit.exsumo.monitoring.ExchangerRepo
import ua.nanit.exsumo.monitoring.data.Exchanger
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.ui.Signal

class ExchangerViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val exchangerRepo: ExchangerRepo
) : ViewModel() {

    private val _exchanger = Signal<Exchanger>()
    private val _url = Signal<String>()

    val exchanger: LiveData<Exchanger> get() = _exchanger
    val url: LiveData<String> get() = _url

    fun load(rate: Rate) {
        viewModelScope.launch(dispatcher) {
            val data = exchangerRepo.provide(rate)
            viewModelScope.launch { _exchanger.setValue(data) }
        }
    }

    fun openWebsite() {
        postUrl(_exchanger.value?.url)
    }

    fun openReviews() {
        postUrl(_exchanger.value?.reviewsUrl)
    }

    private fun postUrl(url: String?) {
        if (url != null) {
            _url.setValue(url!!)
        }
    }

}