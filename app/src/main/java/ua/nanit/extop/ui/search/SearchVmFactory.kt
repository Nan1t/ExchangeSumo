package ua.nanit.extop.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import ua.nanit.extop.ui.BaseVmFactory

class SearchVmFactory(ctx: Context) : BaseVmFactory(ctx) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val factory = getMonitoringFactory()
        return SearchViewModel(factory.createStorage(),
            factory.createCurrenciesProvider()) as T
    }
}