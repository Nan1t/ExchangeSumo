package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BaseFragment
import ua.nanit.extop.ui.requireCompatActionBar

class RatesFragment : BaseFragment(R.layout.fragment_rates) {

    private lateinit var viewModel: RatesViewModel

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var emptyText: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        requireCompatActionBar()?.show()

        viewModel = ViewModelProvider(requireActivity(), RatesVmFactory(requireContext()))
            .get(RatesViewModel::class.java)

        ratesAdapter = RatesAdapter()
        ratesList = view.findViewById(R.id.rates_list)
        swipeRefresh = view.findViewById(R.id.rates_swipe_refresh)
        emptyText = view.findViewById(R.id.rates_list_empty)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(context)

        swipeRefresh.setOnRefreshListener { requestRefresh(false) }

        viewModel.rates.observe(viewLifecycleOwner, this::observeListUpdates)
        viewModel.currencies.observe(viewLifecycleOwner) {
            requireCompatActionBar()?.title = it
        }
    }

    override fun onStart() {
        super.onStart()
        requestRefresh(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                navigation.navigate(R.id.action_nav_rates_to_nav_search)
                true
            }
            R.id.menu_calculator -> {
                true
            }
            else -> false
        }
    }

    private fun observeListUpdates(rates: List<Rate>) {
        swipeRefresh.isRefreshing = false

        if (rates.isEmpty()) {
            emptyText.visibility = View.VISIBLE
            swipeRefresh.visibility = View.GONE
        } else {
            emptyText.visibility = View.GONE
            swipeRefresh.visibility = View.VISIBLE
            ratesAdapter.update(rates)
        }
    }

    private fun requestRefresh(showProgress: Boolean) {
        if (showProgress) {
            swipeRefresh.post {
                swipeRefresh.isRefreshing = true
            }
        }
        viewModel.refreshRates()
    }
}