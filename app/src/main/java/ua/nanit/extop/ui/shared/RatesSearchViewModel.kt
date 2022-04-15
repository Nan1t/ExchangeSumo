package ua.nanit.extop.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ua.nanit.extop.ui.SingleEvent

class RatesSearchViewModel : ViewModel() {

    private var _ratesRefresh = SingleEvent<Unit>()
    val ratesRefresh: LiveData<Unit> get() = _ratesRefresh

    fun signalRefreshRates() {
        _ratesRefresh.setValue(Unit)
    }

}