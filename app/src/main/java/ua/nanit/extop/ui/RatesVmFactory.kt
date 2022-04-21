package ua.nanit.extop.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.extop.ui.base.BaseVmFactory

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