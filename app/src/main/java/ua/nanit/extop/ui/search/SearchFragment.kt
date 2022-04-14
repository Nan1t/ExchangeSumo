package ua.nanit.extop.ui.search

import android.os.Bundle
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.CurrencyType
import ua.nanit.extop.ui.BaseFragment

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private lateinit var viewModel: SearchViewModel

    private lateinit var layoutCurrencyIn: TextInputLayout
    private lateinit var layoutCurrencyOut: TextInputLayout
    private lateinit var fieldCurrencyIn: TextInputEditText
    private lateinit var fieldCurrencyOut: TextInputEditText
    private lateinit var btnSwap: FloatingActionButton
    private lateinit var btnConfirm: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)

        layoutCurrencyIn = view.findViewById(R.id.rates_currency_in_layout)
        layoutCurrencyOut = view.findViewById(R.id.rates_currency_out_layout)
        fieldCurrencyIn = view.findViewById(R.id.rates_currency_in)
        fieldCurrencyOut = view.findViewById(R.id.rates_currency_out)
        btnSwap = view.findViewById(R.id.search_btn_swap)
        btnConfirm = view.findViewById(R.id.search_btn_confirm)

        fieldCurrencyIn.setOnClickListener {
            openCurrenciesMenu()
            viewModel.loadCurrencies(CurrencyType.IN)
        }

        fieldCurrencyOut.setOnClickListener {
            openCurrenciesMenu()
            viewModel.loadCurrencies(CurrencyType.OUT)
        }

        btnSwap.setOnClickListener(this::onSwapClicked)
        btnConfirm.setOnClickListener { viewModel.applySearchParams() }

        viewModel.currencyIn.observe(viewLifecycleOwner) {
            fieldCurrencyIn.setText(it.name)
            layoutCurrencyIn.isErrorEnabled = true
            layoutCurrencyIn.error = null
            layoutCurrencyIn.isErrorEnabled = false
        }

        viewModel.currencyOut.observe(viewLifecycleOwner) {
            fieldCurrencyOut.setText(it.name)
            layoutCurrencyOut.isErrorEnabled = true
            layoutCurrencyOut.error = null
            layoutCurrencyIn.isErrorEnabled = false
        }

        viewModel.applyState.observe(viewLifecycleOwner, this::observeApplyState)

        navigation.hide()
    }

    private fun observeApplyState(state: ApplyState) {
        when (state) {
            ApplyState.SUCCESS -> {
                navigation.navigateUp()
            }
            ApplyState.NO_CURRENCY_IN -> {
                layoutCurrencyIn.isErrorEnabled = true
                layoutCurrencyIn.error = "NO_CURRENCY_IN"
            }
            ApplyState.NO_CURRENCY_OUT -> {
                layoutCurrencyOut.isErrorEnabled = true
                layoutCurrencyOut.error = "NO_CURRENCY_OUT"
            }
        }
    }

    private fun onSwapClicked(view: View) {
        btnSwap.animate()
            .setDuration(300)
            .rotation(180f)
            .withEndAction { view.rotation = 0.0F }
            .setInterpolator(FastOutSlowInInterpolator())
            .start()
        viewModel.swapCurrencies()
    }

    private fun openCurrenciesMenu() {
        navigation.navigate(R.id.action_nav_search_to_nav_currencies)
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

}