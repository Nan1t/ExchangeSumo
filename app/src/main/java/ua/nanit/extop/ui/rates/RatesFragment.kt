package ua.nanit.extop.ui.rates

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BaseFragment
import ua.nanit.extop.ui.requireCompatActionBar
import ua.nanit.extop.ui.shared.SharedViewModel

class RatesFragment : BaseFragment(R.layout.fragment_rates) {

    private lateinit var viewModel: RatesViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var emptyText: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var calculatorDialog: AlertDialog
    private lateinit var rateSelectAction: RateBottomSheet

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity(), RatesVmFactory(requireContext()))
            .get(RatesViewModel::class.java)
        sharedViewModel = sharedViewModel()

        calculatorDialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.calc_title)
            .setView(R.layout.dialog_calculator)
            .setPositiveButton(R.string.apply) { _, _ ->
                // TODO calculate
            }.create()

        rateSelectAction = RateBottomSheet()

        sharedViewModel.signalRefreshRates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesAdapter = RatesAdapter(this::onRateClicked)
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
                navigation.navToSearch()
                true
            }
            R.id.menu_calculator -> {
                calculatorDialog.show()
                true
            }
            else -> false
        }
    }

    private fun onRateClicked(rate: Rate) {
        rateSelectAction.title = rate.exchanger
        rateSelectAction.linkClickListener = View.OnClickListener {
            rateSelectAction.hide()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rate.link)))
        }
        rateSelectAction.infoClickListener = View.OnClickListener {
            rateSelectAction.hide()
            sharedViewModel.signalRateInfo(rate)
            navigation.navToExchanger()
        }
        rateSelectAction.show(parentFragmentManager, RateBottomSheet.TAG)
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