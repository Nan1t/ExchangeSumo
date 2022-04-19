package ua.nanit.extop.ui.doublex

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.DoubleExchange
import ua.nanit.extop.ui.BaseRateAdapter
import ua.nanit.extop.ui.BaseRatesFragment

class DoubleExchangeFragment : BaseRatesFragment<DoubleExchange>(R.layout.fragment_double) {

    override fun getViewModel(): DoubleExchangeVm {
        return ViewModelProvider(requireActivity(), DoubleExchangeVmFactory(requireContext()))
            .get(DoubleExchangeVm::class.java)
    }

    override fun createAdapter(): BaseRateAdapter<DoubleExchange, *> {
        return DoubleExchangeAdapter(this::onRateClicked)
    }

    override fun findListView(view: View): RecyclerView {
        return view.findViewById(R.id.double_list)
    }

    override fun findEmptyListView(view: View): TextView {
        return view.findViewById(R.id.double_list_empty)
    }

    override fun findSwipeRefresh(view: View): SwipeRefreshLayout {
        return view.findViewById(R.id.double_swipe_refresh)
    }

    override fun onRateClicked(rate: DoubleExchange) {
        Logger.info("Double rate clicked")
    }
}