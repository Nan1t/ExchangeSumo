package ua.nanit.extop.ui.rates

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BaseFragment
import ua.nanit.extop.ui.requireCompatActionBar
import ua.nanit.extop.ui.shared.RatesSearchViewModel

class RatesFragment : BaseFragment(R.layout.fragment_rates) {

    private lateinit var viewModel: RatesViewModel
    private lateinit var sharedViewModel: RatesSearchViewModel

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var emptyText: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var waitForUpdate = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity(), RatesVmFactory(requireContext()))
            .get(RatesViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity())
            .get(RatesSearchViewModel::class.java)

        sharedViewModel.signalRefreshRates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        requireCompatActionBar()?.show()

        ratesAdapter = RatesAdapter()
        ratesList = view.findViewById(R.id.rates_list)
        swipeRefresh = view.findViewById(R.id.rates_swipe_refresh)
        emptyText = view.findViewById(R.id.rates_list_empty)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(context)

        swipeRefresh.setOnRefreshListener { requestRefresh(false) }

        viewModel.error.observe(viewLifecycleOwner, this::observeError)
        viewModel.rates.observe(viewLifecycleOwner, this::observeListUpdates)
        viewModel.currencies.observe(viewLifecycleOwner) { requireCompatActionBar()?.title = it }
        sharedViewModel.ratesRefresh.observe(viewLifecycleOwner) { requestRefresh(true) }
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
                openCalculator()
                true
            }
            else -> false
        }
    }

    private fun requestRefresh(animate: Boolean) {
        if (animate) {
            swipeRefresh.visibility = View.VISIBLE
            emptyText.visibility = View.GONE

            swipeRefresh.post {
                swipeRefresh.isRefreshing = true
            }
        }

        viewModel.refreshRates()
    }

    private fun openCalculator() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.toolbar_calculator)
        dialog.setView(R.layout.dialog_calculator)
        dialog.setPositiveButton("OK") { _, _ ->

        }
        dialog.create().show()
    }

    /* Observers */

    private fun observeListUpdates(rates: List<Rate>) {
        swipeRefresh.isRefreshing = false

        if (rates.isEmpty()) {
            swipeRefresh.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        } else {
            ratesAdapter.update(rates)
        }
    }

    private fun observeError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}