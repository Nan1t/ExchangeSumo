package ua.nanit.exsumo.ui.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.exsumo.R
import ua.nanit.exsumo.databinding.DialogCalculatorBinding
import ua.nanit.exsumo.monitoring.Direction
import ua.nanit.exsumo.ui.RatesViewModel
import ua.nanit.exsumo.ui.RatesVmFactory
import ua.nanit.exsumo.ui.shared.SharedViewModel
import java.lang.IllegalArgumentException

abstract class BaseRatesFragment<T> : BaseFragment() {

    protected lateinit var viewModel: RatesViewModel
    protected lateinit var sharedViewModel: SharedViewModel

    private lateinit var ratesAdapter: BaseRateAdapter<T, *>
    private lateinit var ratesList: RecyclerView
    private lateinit var emptyList: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var calculatorDialog: AlertDialog
    private lateinit var calcRadioGroup: RadioGroup
    private lateinit var calcAmount: TextInputEditText

    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        actionBar = requireCompatActionBar() ?: return
        viewModel = ViewModelProvider(requireActivity(), RatesVmFactory(requireContext()))
            .get(RatesViewModel::class.java)
        sharedViewModel = sharedViewModel()

        setupCalcDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesAdapter = createAdapter()
        ratesList = getListView()
        emptyList = getEmptyListView()
        swipeRefresh = getSwipeRefresh()

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(requireContext())

        swipeRefresh.setOnRefreshListener { refreshRates() }

        viewModel.currencies.observe(viewLifecycleOwner) { actionBar.title = it }
        viewModel.error.observe(viewLifecycleOwner, ::observeError)
        sharedViewModel.ratesRefresh.observe(viewLifecycleOwner) { refreshRates() }
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

    protected abstract fun createAdapter(): BaseRateAdapter<T, *>

    protected abstract fun getListView(): RecyclerView

    protected abstract fun getEmptyListView(): TextView

    protected abstract fun getSwipeRefresh(): SwipeRefreshLayout

    protected abstract fun onRateClicked(rate: T)

    protected abstract fun calculateRates(dir: Direction, amount: Double)

    protected abstract fun requestRefresh()

    protected fun updateList(rates: List<T>) {
        setSwipeRefreshing(false)

        if (rates.isEmpty()) {
            ratesList.visibility = View.GONE
            emptyList.visibility = View.VISIBLE
            return
        }

        ratesList.visibility = View.VISIBLE
        emptyList.visibility = View.GONE

        ratesAdapter.update(rates)
    }

    protected fun setSwipeRefreshing(refresh: Boolean) {
        if (swipeRefresh.isRefreshing != refresh)
            swipeRefresh.post { swipeRefresh.isRefreshing = refresh }
    }

    private fun refreshRates() {
        setSwipeRefreshing(true)
        requestRefresh()
    }

    private fun observeError(msg: String) {
        setSwipeRefreshing(false)
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun setupCalcDialog() {
        val binding = DialogCalculatorBinding.inflate(layoutInflater)

        calculatorDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.calc_title)
            .setView(binding.root)
            .setPositiveButton(R.string.apply) { _, _ ->
                val amount = binding.calcAmount.text.toString().toDoubleOrNull() ?: 0.0
                val dir = when (binding.calcRadioGroup.checkedRadioButtonId) {
                    R.id.calc_radio_in -> Direction.IN
                    R.id.calc_radio_out -> Direction.OUT
                    else -> throw IllegalArgumentException("Undefined calc direction")
                }
                calculateRates(dir, amount)
            }
            .create()
    }

}