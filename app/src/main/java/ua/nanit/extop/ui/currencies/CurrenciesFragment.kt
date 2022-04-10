package ua.nanit.extop.ui.currencies

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.ui.BaseFragment

class CurrenciesFragment : BaseFragment(R.layout.fragment_currencies) {

    private lateinit var searchField: EditText
    private lateinit var btnConfirm: ImageButton
    private lateinit var list: RecyclerView
    private lateinit var listAdapter: CurrencyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchField = view.findViewById(R.id.currencies_search_field)
        btnConfirm = view.findViewById(R.id.currencies_btn_confirm)
        list = view.findViewById(R.id.currencies_list)

        listAdapter = CurrencyAdapter(this::onCurrencyClicked)
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = listAdapter

        btnConfirm.visibility = View.GONE
        btnConfirm.setOnClickListener {
            if (listAdapter.selected != null) {
                viewModel.selectCurrency(listAdapter.selected!!)
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.currencies.observe(viewLifecycleOwner, this::observeCurrencies)
    }

    private fun onCurrencyClicked() {
        btnConfirm.visibility = View.VISIBLE
    }

    private fun observeCurrencies(currencies: List<Currency>) {
        listAdapter.update(currencies)
    }

}