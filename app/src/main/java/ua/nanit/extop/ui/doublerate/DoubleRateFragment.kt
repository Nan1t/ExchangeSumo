package ua.nanit.extop.ui.doublerate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.data.DoubleRate
import ua.nanit.extop.ui.base.BaseRateAdapter
import ua.nanit.extop.ui.base.BaseRatesFragment
import ua.nanit.extop.util.toRawString

class DoubleRateFragment : BaseRatesFragment<DoubleRate>(R.layout.fragment_double) {

    private lateinit var rateSelectAction: DoubleRateBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateSelectAction = DoubleRateBottomSheet()

        if (viewModel.doubleRates.value == null) showLoading()

        viewModel.refreshDoubleRates(true)

        viewModel.doubleRates.observe(viewLifecycleOwner, ::observeRateUpdates)
    }

    override fun createAdapter(): BaseRateAdapter<DoubleRate, *> {
        return DoubleRateAdapter(this::onRateClicked)
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

    override fun onRateClicked(rate: DoubleRate) {
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

        rateSelectAction.show(parentFragmentManager)
    }

    override fun requestRefresh() {
        viewModel.refreshDoubleRates()
    }

    override fun calculateRates(dir: Direction, amount: Double) {
        viewModel.calculateDoubleRates(amount, dir)
    }

    private fun observeRateUpdates(rates: List<DoubleRate>) {
        setSwipeRefreshing(false)
        ratesAdapter.update(rates)
    }

    private fun openLink(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}