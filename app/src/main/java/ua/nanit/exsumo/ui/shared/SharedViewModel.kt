package ua.nanit.exsumo.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.ui.Signal

class SharedViewModel : ViewModel() {

    private var _ratesRefresh = Signal<Unit>()
    private var _rateInfo = Signal<Rate>()

    val ratesRefresh: LiveData<Unit> get() = _ratesRefresh
    val rateInfo: LiveData<Rate> get() = _rateInfo

    fun signalRateInfo(rate: Rate) {
        _rateInfo.setValue(rate)
    }

    fun signalRefreshRates() {
        _ratesRefresh.setValue(Unit)
    }

}