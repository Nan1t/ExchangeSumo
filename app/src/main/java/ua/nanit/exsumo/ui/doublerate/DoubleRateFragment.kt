package ua.nanit.exsumo.ui.doublerate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.exsumo.databinding.FragmentDoubleBinding
import ua.nanit.exsumo.monitoring.Direction
import ua.nanit.exsumo.monitoring.data.DoubleRate
import ua.nanit.exsumo.ui.base.BaseRateAdapter
import ua.nanit.exsumo.ui.base.BaseRatesFragment
import ua.nanit.exsumo.util.toRawString

class DoubleRateFragment : BaseRatesFragment<DoubleRate>() {

    private lateinit var selectedRateSheet: DoubleRateBottomSheet
    private lateinit var binding: FragmentDoubleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoubleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedRateSheet = DoubleRateBottomSheet()
        viewModel.doubleRates.observe(viewLifecycleOwner) { updateList(it) }
        sharedViewModel.doubleRatesRefresh.observe(viewLifecycleOwner) { refreshRates() }
    }

    override fun createAdapter(): BaseRateAdapter<DoubleRate, *> {
        return DoubleRateAdapter(this::onRateClicked)
    }

    override fun getListView(): RecyclerView {
        return binding.doubleList
    }

    override fun getEmptyListView(): TextView {
        return binding.doubleListEmpty
    }

    override fun getSwipeRefresh(): SwipeRefreshLayout {
        return binding.doubleSwipeRefresh
    }

    override fun onRateClicked(rate: DoubleRate) {
        selectedRateSheet.firstAmountIn = rate.amountIn.toRawString()
        selectedRateSheet.firstAmountOut = rate.amountTransit.toRawString()
        selectedRateSheet.firstCurrencyIn = rate.currencyIn
        selectedRateSheet.firstCurrencyOut = rate.currencyTransit
        selectedRateSheet.firstExchanger = rate.firstExchanger

        selectedRateSheet.secondAmountIn = rate.amountTransit.toRawString()
        selectedRateSheet.secondAmountOut = rate.amountOut.toRawString()
        selectedRateSheet.secondCurrencyIn = rate.currencyTransit
        selectedRateSheet.secondCurrencyOut = rate.currencyOut
        selectedRateSheet.secondExchanger = rate.secondExchanger

        selectedRateSheet.firstStepClick = { openLink(rate.firstLink) }
        selectedRateSheet.secondStepClick = { openLink(rate.secondLink) }

        selectedRateSheet.show(parentFragmentManager)
    }

    override fun requestRefresh() {
        viewModel.refreshDoubleRates()
    }

    override fun calculateRates(dir: Direction, amount: Double) {
        viewModel.calculateDoubleRates(amount, dir)
    }

    private fun openLink(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}