package ua.nanit.exsumo.ui.exchanger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _exchanger = MutableLiveData<Exchanger>()
    private val _url = Signal<String>()
    private val _error = Signal<Unit>()

    val exchanger: LiveData<Exchanger> get() = _exchanger
    val url: LiveData<String> get() = _url
    val error: LiveData<Unit> get() = _error

    fun load(rate: Rate) {
        _exchanger.value = Exchanger.EMPTY

        viewModelScope.launch(dispatcher) {
            try {
                val data = exchangerRepo.provide(rate)
                viewModelScope.launch { _exchanger.setValue(data) }
            } catch (th: Throwable) {
                viewModelScope.launch { _error.setValue(Unit) }
            }
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