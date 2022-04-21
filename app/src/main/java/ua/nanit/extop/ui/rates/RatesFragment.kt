package ua.nanit.extop.ui.rates

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.base.BaseRateAdapter
import ua.nanit.extop.ui.base.BaseRatesFragment

class RatesFragment : BaseRatesFragment<Rate>(R.layout.fragment_rates) {

    private lateinit var rateSelectAction: RateBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateSelectAction = RateBottomSheet()

        if (viewModel.rates.value == null) showLoading()

        viewModel.refreshRates(true)

        viewModel.rates.observe(viewLifecycleOwner, ::observeRateUpdates)
    }

    override fun createAdapter(): BaseRateAdapter<Rate, *> {
        return RatesAdapter(this::onRateClicked)
    }

    override fun findListView(view: View): RecyclerView {
        return view.findViewById(R.id.rates_list)
    }

    override fun findEmptyListView(view: View): TextView {
        return view.findViewById(R.id.rates_list_empty)
    }

    override fun findSwipeRefresh(view: View): SwipeRefreshLayout {
        return view.findViewById(R.id.rates_swipe_refresh)
    }

    override fun onRateClicked(rate: Rate) {
        rateSelectAction.title = rate.exchanger
        rateSelectAction.isManual = rate.isManual
        rateSelectAction.isMediator = rate.isMediator
        rateSelectAction.isCardVerify = rate.isCardVerify

        rateSelectAction.linkClickListener = View.OnClickListener {
            rateSelectAction.hide()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rate.link)))
        }

        rateSelectAction.infoClickListener = View.OnClickListener {
            rateSelectAction.hide()
            sharedViewModel.signalRateInfo(rate)
            navigation.navToExchanger()
        }

        rateSelectAction.show(parentFragmentManager)
    }

    override fun requestRefresh() {
        viewModel.refreshRates()
    }

    private fun observeRateUpdates(rates: List<Rate>) {
        setSwipeRefreshing(false)
        ratesAdapter.update(rates)
    }

    override fun calculateRates(dir: Direction, amount: Double) {
        viewModel.calculateRates(amount, dir)
    }
}