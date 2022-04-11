package ua.nanit.extop.ui.pages

import android.os.Bundle
import android.view.View
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.ui.BaseFragment
import ua.nanit.extop.ui.MainViewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private lateinit var fieldCurrencyIn: TextInputEditText
    private lateinit var fieldCurrencyOut: TextInputEditText
    private lateinit var btnSwap: FloatingActionButton
    private lateinit var btnConfirm: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldCurrencyIn = view.findViewById(R.id.rates_currency_in)
        fieldCurrencyOut = view.findViewById(R.id.rates_currency_out)
        btnSwap = view.findViewById(R.id.search_btn_swap)
        btnConfirm = view.findViewById(R.id.search_btn_confirm)

        fieldCurrencyIn.setOnClickListener {
            viewModel.openCurrenciesMenu(MainViewModel.CURRENCIES_MENU_IN)
        }

        fieldCurrencyOut.setOnClickListener {
            viewModel.openCurrenciesMenu(MainViewModel.CURRENCIES_MENU_OUT)
        }

        btnSwap.setOnClickListener(this::onSwapClicked)

        viewModel.currencyIn.observe(viewLifecycleOwner, this::observeCurrencyIn)
        viewModel.currencyOut.observe(viewLifecycleOwner, this::observeCurrencyOut)
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

    private fun observeCurrencyIn(currency: Currency) {
        fieldCurrencyIn.setText(currency.name)
    }

    private fun observeCurrencyOut(currency: Currency) {
        fieldCurrencyOut.setText(currency.name)
    }

}