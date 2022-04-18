package ua.nanit.extop.ui.search

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.ui.BaseFragment

class CurrenciesFragment : BaseFragment(R.layout.fragment_currencies) {

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchField: EditText
    private lateinit var btnConfirm: ImageButton
    private lateinit var list: RecyclerView
    private lateinit var listAdapter: CurrencyAdapter
    private lateinit var watcher: TextWatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)

        searchField = view.findViewById(R.id.currencies_search_field)
        btnConfirm = view.findViewById(R.id.currencies_btn_confirm)
        list = view.findViewById(R.id.currencies_list)

        listAdapter = CurrencyAdapter(this::onCurrencyClicked)
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = listAdapter

        btnConfirm.visibility = View.GONE
        btnConfirm.setOnClickListener(this::onConfirmClick)

        watcher = searchField.addTextChangedListener {
            val value = it?.toString()
            if (value != null) {
                listAdapter.filter(value)
            }
        }

        viewModel.currencies.observe(viewLifecycleOwner, this::observeCurrencies)
    }

    override fun onResume() {
        super.onResume()
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

    private fun onConfirmClick(view: View) {
        if (listAdapter.selected != null) {
            viewModel.selectCurrency(listAdapter.selected!!)
            navigation.navigateUp()
        }
    }

    private fun onCurrencyClicked() {
        btnConfirm.visibility = View.VISIBLE
    }

    private fun observeCurrencies(currencies: List<Currency>) {
        listAdapter.update(currencies)
    }
}