package ua.nanit.extop.ui.rates

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.extop.ui.BaseVmFactory

class RatesVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()

        return RatesViewModel(
            Dispatchers.IO,
            factory.createStorage(),
            factory.createRatesProvider(),
            factory.createCalculator()
        ) as T
    }

}