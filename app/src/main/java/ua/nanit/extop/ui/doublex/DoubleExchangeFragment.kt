package ua.nanit.extop.ui.doublex

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import ua.nanit.extop.util.toRawString

class DoubleExchangeFragment : BaseRatesFragment<DoubleExchange>(R.layout.fragment_double) {

    private lateinit var rateSelectAction: DoubleExchangeBottomSheet

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rateSelectAction = DoubleExchangeBottomSheet()
    }

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
        rateSelectAction.firstAmountIn = rate.amountIn.toRawString()
        rateSelectAction.firstAmountOut = rate.amountTransit.toRawString()
        rateSelectAction.firstCurrencyIn = rate.currencyIn
        rateSelectAction.firstCurrencyOut = rate.currencyTransit
        rateSelectAction.firstExchanger = rate.firstExchanger

        rateSelectAction.secondAmountIn = rate.amountTransit.toRawString()
        rateSelectAction.secondAmountOut = rate.amountOut.toRawString()
        rateSelectAction.secondCurrencyIn = rate.currencyTransit
        rateSelectAction.secondCurrencyOut = rate.currencyOut
        rateSelectAction.secondExchanger = rate.secondExchanger

        rateSelectAction.firstStepClick = { openLink(rate.firstLink) }
        rateSelectAction.secondStepClick = { openLink(rate.secondLink) }

        rateSelectAction.show(parentFragmentManager, DoubleExchangeBottomSheet.TAG)
    }

    private fun openLink(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}