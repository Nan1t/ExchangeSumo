package ua.nanit.exsumo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.exsumo.ui.base.BaseVmFactory

class RatesVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()

        return RatesViewModel(
            Dispatchers.IO,
            factory.getStorage(),
            factory.getRatesRepo(),
            factory.getDoubleExchangeRepo(),
            factory.getCalculator()
        ) as T
    }

}