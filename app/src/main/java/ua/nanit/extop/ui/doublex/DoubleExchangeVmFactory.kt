package ua.nanit.extop.ui.doublex

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.extop.ui.BaseVmFactory

class DoubleExchangeVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()

        return DoubleExchangeVm(
            Dispatchers.IO,
            factory.getStorage(),
            factory.getDoubleExchangeRepo()
        ) as T
    }
}