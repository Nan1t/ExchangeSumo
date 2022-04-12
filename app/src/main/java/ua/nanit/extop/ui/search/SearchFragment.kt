package ua.nanit.extop.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.CurrencyType
import ua.nanit.extop.requireCompatActionBar

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var viewModel: SearchViewModel
    private lateinit var navController: NavController

    private lateinit var fieldCurrencyIn: TextInputEditText
    private lateinit var fieldCurrencyOut: TextInputEditText
    private lateinit var btnSwap: FloatingActionButton
    private lateinit var btnConfirm: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireCompatActionBar()?.hide()

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)
        navController = findNavController()

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

        viewModel.currencyIn.observe(viewLifecycleOwner) { fieldCurrencyIn.setText(it.name) }
        viewModel.currencyOut.observe(viewLifecycleOwner) { fieldCurrencyOut.setText(it.name) }
        viewModel.applyParamsCallback.observe(viewLifecycleOwner) {
            when (it) {
                SearchViewModel.RESULT_SUCCESS -> {
                    Logger.info("Success")
                }
                SearchViewModel.RESULT_MISSING_CURRENCY_IN -> {
                    fieldCurrencyIn.error = "Missing"
                }
                SearchViewModel.RESULT_MISSING_CURRENCY_OUT -> {
                    fieldCurrencyOut.error = "Missing"
                }
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
        navController.navigate(R.id.action_nav_search_to_nav_currencies)
    }

}