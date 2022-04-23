package ua.nanit.exsumo.ui.rates

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.exsumo.databinding.FragmentRatesBinding
import ua.nanit.exsumo.monitoring.Direction
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.ui.base.BaseRateAdapter
import ua.nanit.exsumo.ui.base.BaseRatesFragment

class RatesFragment : BaseRatesFragment<Rate>() {

    private lateinit var selectedRateSheet: RateBottomSheet
    private lateinit var binding: FragmentRatesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedRateSheet = RateBottomSheet()

        if (viewModel.rates.value == null) showLoading()

        viewModel.refreshRates(true)

        viewModel.rates.observe(viewLifecycleOwner, ::observeRateUpdates)
    }

    override fun createAdapter(): BaseRateAdapter<Rate, *> {
        return RatesAdapter(this::onRateClicked)
    }

    override fun getListView(): RecyclerView {
        return binding.ratesList
    }

    override fun getEmptyListView(): TextView {
        return binding.ratesListEmpty
    }

    override fun getSwipeRefresh(): SwipeRefreshLayout {
        return binding.ratesSwipeRefresh
    }

    override fun onRateClicked(rate: Rate) {
        selectedRateSheet.title = rate.exchanger
        selectedRateSheet.isManual = rate.isManual
        selectedRateSheet.isMediator = rate.isMediator
        selectedRateSheet.isCardVerify = rate.isCardVerify

        selectedRateSheet.linkClickListener = View.OnClickListener {
            selectedRateSheet.hide()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rate.link)))
        }

        selectedRateSheet.infoClickListener = View.OnClickListener {
            selectedRateSheet.hide()
            sharedViewModel.signalRateInfo(rate)
            navigation.navToExchanger()
        }

        selectedRateSheet.show(parentFragmentManager)
    }

    override fun requestRefresh() {
        viewModel.refreshRates()
    }

    private fun observeRateUpdates(rates: List<Rate>) {
        setSwipeRefreshing(false)
        updateList(rates)
    }

    override fun calculateRates(dir: Direction, amount: Double) {
        viewModel.calculateRates(amount, dir)
    }
}