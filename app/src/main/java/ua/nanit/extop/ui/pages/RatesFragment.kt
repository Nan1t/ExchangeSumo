package ua.nanit.extop.ui.pages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BasePage
import ua.nanit.extop.ui.rates.RatesAdapter

class RatesFragment : BasePage(R.layout.fragment_rates) {

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesAdapter = RatesAdapter()
        ratesList = view.findViewById(R.id.rates_list)
        swipeRefresh = view.findViewById(R.id.rates_swipe_refresh)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(context)

        swipeRefresh.setOnRefreshListener(viewModel::refreshRates)

        viewModel.rates.observe(viewLifecycleOwner, this::observeRates)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.rates.removeObservers(this)
    }

    private fun observeRates(rates: List<Rate>) {
        ratesAdapter.update(rates)
        swipeRefresh.isRefreshing = false
        Logger.info("Rates refreshed: $rates")
    }

}