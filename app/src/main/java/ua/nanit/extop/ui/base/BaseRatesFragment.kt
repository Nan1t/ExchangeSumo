package ua.nanit.extop.ui.base

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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.ui.RatesViewModel
import ua.nanit.extop.ui.RatesVmFactory
import ua.nanit.extop.ui.shared.SharedViewModel
import java.lang.IllegalArgumentException

abstract class BaseRatesFragment<T>(layoutId: Int) : BaseFragment(layoutId) {

    protected lateinit var viewModel: RatesViewModel
    protected lateinit var sharedViewModel: SharedViewModel

    protected lateinit var ratesAdapter: BaseRateAdapter<T, *>
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
        ratesList = findListView(view)
        emptyList = findEmptyListView(view)
        swipeRefresh = findSwipeRefresh(view)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(requireContext())

        swipeRefresh.setOnRefreshListener { refreshRates() }

        viewModel.currencies.observe(viewLifecycleOwner) { actionBar.title = it }
        viewModel.emptyRates.observe(viewLifecycleOwner) { observeEmptyRates() }
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

    protected abstract fun findListView(view: View): RecyclerView

    protected abstract fun findEmptyListView(view: View): TextView

    protected abstract fun findSwipeRefresh(view: View): SwipeRefreshLayout

    protected abstract fun onRateClicked(rate: T)

    protected abstract fun calculateRates(dir: Direction, amount: Double)

    protected abstract fun requestRefresh()

    protected fun setSwipeRefreshing(refresh: Boolean) {
        if (swipeRefresh.isRefreshing != refresh)
            swipeRefresh.post { swipeRefresh.isRefreshing = refresh }
    }

    protected fun showLoading() {
        swipeRefresh.visibility = View.VISIBLE
        emptyList.visibility = View.GONE
        setSwipeRefreshing(true)
    }

    private fun refreshRates() {
        showLoading()
        requestRefresh()
    }

    private fun observeEmptyRates() {
        setSwipeRefreshing(false)
        swipeRefresh.visibility = View.GONE
        emptyList.visibility = View.VISIBLE
    }

    private fun observeError(msg: String) {
        setSwipeRefreshing(false)
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun setupCalcDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_calculator, null)

        calcRadioGroup = dialogView.findViewById(R.id.calc_radio_group) ?: return
        calcAmount = dialogView.findViewById(R.id.calc_amount) ?: return

        calculatorDialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.calc_title)
            .setView(dialogView)
            .setPositiveButton(R.string.apply) { _, _ ->
                val amount = calcAmount.text.toString().toDoubleOrNull() ?: 0.0
                val dir = when (calcRadioGroup.checkedRadioButtonId) {
                    R.id.calc_radio_in -> Direction.IN
                    R.id.calc_radio_out -> Direction.OUT
                    else -> throw IllegalArgumentException("Undefined calc direction")
                }
                calculateRates(dir, amount)
            }
            .create()
    }

}