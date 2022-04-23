package ua.nanit.exsumo.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ua.nanit.exsumo.ui.base.BaseVmFactory

class SearchVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()
        return SearchViewModel(
            Dispatchers.IO,
            factory.getStorage(),
            factory.getCurrenciesRepo()
        ) as T
    }
}