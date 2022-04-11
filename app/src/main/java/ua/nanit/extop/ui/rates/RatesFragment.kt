package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.Rate

class RatesFragment : Fragment(R.layout.fragment_rates) {

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var nothingText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesAdapter = RatesAdapter()
        ratesList = view.findViewById(R.id.rates_list)
        swipeRefresh = view.findViewById(R.id.rates_swipe_refresh)
        nothingText = view.findViewById(R.id.rates_list_nothing_test)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(context)

        Logger.info("Created rates")

//        swipeRefresh.setOnRefreshListener(viewModel::refreshRates)
//
//        viewModel.rates.observe(viewLifecycleOwner, this::observeRates)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.info("Destroyed rates")
//        viewModel.rates.removeObservers(this)
    }

    private fun observeRates(rates: List<Rate>) {
        if(rates.isNotEmpty()) {
            ratesAdapter.update(rates)
            swipeRefresh.isRefreshing = false
            nothingText.visibility = View.GONE
            Logger.info("Rates refreshed: $rates")
        } else {
            nothingText.visibility = View.VISIBLE
        }
    }

}