package ua.nanit.extop.ui.search

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.Currency

class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {

    private lateinit var viewModel: SearchViewModel
    private lateinit var navController: NavController

    private lateinit var navbar: BottomNavigationView
    private lateinit var searchField: EditText
    private lateinit var btnConfirm: ImageButton
    private lateinit var list: RecyclerView
    private lateinit var listAdapter: CurrencyAdapter
    private lateinit var watcher: TextWatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)
        navController = findNavController()

        navbar = requireActivity().findViewById(R.id.main_navbar)
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

        navbar.visibility = View.GONE
    }

    private fun onConfirmClick(view: View) {
        if (listAdapter.selected != null) {
            viewModel.selectCurrency(listAdapter.selected!!)
            navController.navigateUp()
        }
    }

    private fun onCurrencyClicked() {
        btnConfirm.visibility = View.VISIBLE
    }

    private fun observeCurrencies(currencies: List<Currency>) {
        listAdapter.update(currencies)
    }

    override fun onStop() {
        super.onStop()
        navbar.visibility = View.VISIBLE
    }
}