package ua.nanit.exsumo.ui.exchanger

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.exsumo.ui.base.BaseVmFactory

class ExchangerVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()

        return ExchangerViewModel(
            Dispatchers.IO,
            factory.getExchangerRepo()
        ) as T
    }

}