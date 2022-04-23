package ua.nanit.exsumo.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.nanit.exsumo.databinding.FragmentCurrenciesBinding
import ua.nanit.exsumo.monitoring.data.Currency
import ua.nanit.exsumo.ui.base.BaseFragment

class CurrenciesFragment : BaseFragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentCurrenciesBinding
    private lateinit var listAdapter: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)

        listAdapter = CurrencyAdapter(this::onCurrencyClicked)
        binding.currenciesList.layoutManager = LinearLayoutManager(requireContext())
        binding.currenciesList.adapter = listAdapter

        binding.currenciesBtnConfirm.visibility = View.GONE
        binding.currenciesBtnConfirm.setOnClickListener(this::onConfirmClick)

        binding.currenciesSearchField.addTextChangedListener {
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
        binding.currenciesBtnConfirm.visibility = View.VISIBLE
    }

    private fun observeCurrencies(currencies: List<Currency>) {
        listAdapter.update(currencies)
    }
}