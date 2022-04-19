package ua.nanit.extop.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import ua.nanit.extop.log.Logger

abstract class BaseRatesFragment<T>(layoutId: Int) : BaseFragment(layoutId) {

    private lateinit var viewModel: BaseRatesVm<T>

    private lateinit var ratesAdapter: BaseRateAdapter<T, *>
    private lateinit var ratesList: RecyclerView
    private lateinit var emptyList: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = getViewModel()
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesAdapter = createAdapter()
        ratesList = findListView(view)
        emptyList = findEmptyListView(view)
        swipeRefresh = findSwipeRefresh(view)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(requireContext())

        swipeRefresh.setOnRefreshListener(::requestRefresh)

        if (viewModel.rates.value == null) {
            showLoading()
        }

        viewModel.emptyRates.observe(viewLifecycleOwner) { observeEmptyRates() }
        viewModel.rates.observe(viewLifecycleOwner, ::observeListUpdates)
        viewModel.error.observe(viewLifecycleOwner, ::observeError)
    }

    protected abstract fun getViewModel(): BaseRatesVm<T>

    protected abstract fun createAdapter(): BaseRateAdapter<T, *>

    protected abstract fun findListView(view: View): RecyclerView

    protected abstract fun findEmptyListView(view: View): TextView

    protected abstract fun findSwipeRefresh(view: View): SwipeRefreshLayout

    protected abstract fun onRateClicked(rate: T)

    protected fun requestRefresh() {
        showLoading()
        viewModel.refresh()
    }

    private fun showLoading() {
        swipeRefresh.visibility = View.VISIBLE
        emptyList.visibility = View.GONE
        swipeRefresh.post { swipeRefresh.isRefreshing = true }
    }

    private fun observeEmptyRates() {
        swipeRefresh.post { swipeRefresh.isRefreshing = false }
        swipeRefresh.visibility = View.GONE
        emptyList.visibility = View.VISIBLE
    }

    private fun observeListUpdates(rates: List<T>) {
        swipeRefresh.post { swipeRefresh.isRefreshing = false }
        ratesAdapter.update(rates)
    }

    private fun observeError(msg: String) {
        swipeRefresh.post { swipeRefresh.isRefreshing = false }
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

}