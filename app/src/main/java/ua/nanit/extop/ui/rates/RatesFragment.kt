package ua.nanit.extop.ui.rates

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.*
import ua.nanit.extop.ui.shared.SharedViewModel

class RatesFragment : BaseRatesFragment<Rate>(R.layout.fragment_rates) {

    private lateinit var viewModel: RatesViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var calculatorDialog: AlertDialog
    private lateinit var rateSelectAction: RateBottomSheet

    private lateinit var calcRadioGroup: RadioGroup
    private lateinit var calcAmount: TextInputEditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)

        viewModel = getViewModel()
        sharedViewModel = sharedViewModel()

        val dialogView = layoutInflater.inflate(R.layout.dialog_calculator, null)

        calcRadioGroup = dialogView.findViewById(R.id.calc_radio_group) ?: return
        calcAmount = dialogView.findViewById(R.id.calc_amount) ?: return

        calculatorDialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.calc_title)
            .setView(dialogView)
            .setPositiveButton(R.string.apply) { _, _ -> calculate() }
            .create()

        rateSelectAction = RateBottomSheet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currencies.observe(viewLifecycleOwner) { requireCompatActionBar()?.title = it }
        sharedViewModel.ratesRefresh.observe(viewLifecycleOwner) { requestRefresh() }
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

    override fun getViewModel(): RatesViewModel {
        return ViewModelProvider(requireActivity(), RatesVmFactory(requireContext()))
            .get(RatesViewModel::class.java)
    }

    override fun createAdapter(): BaseRateAdapter<Rate, *> {
        return RatesAdapter(this::onRateClicked)
    }

    override fun findListView(view: View): RecyclerView {
        return view.findViewById(R.id.rates_list)
    }

    override fun findEmptyListView(view: View): TextView {
        return view.findViewById(R.id.rates_list_empty)
    }

    override fun findSwipeRefresh(view: View): SwipeRefreshLayout {
        return view.findViewById(R.id.rates_swipe_refresh)
    }

    override fun onRateClicked(rate: Rate) {
        rateSelectAction.title = rate.exchanger
        rateSelectAction.isManual = rate.isManual
        rateSelectAction.isMediator = rate.isMediator
        rateSelectAction.isCardVerify = rate.isCardVerify

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

    private fun calculate() {
        val amount = calcAmount.text.toString().toDoubleOrNull() ?: return

        when (calcRadioGroup.checkedRadioButtonId) {
            R.id.calc_radio_in -> {
                viewModel.calculate(amount, Direction.IN)
            }
            R.id.calc_radio_out -> {
                viewModel.calculate(amount, Direction.OUT)
            }
        }
    }
}