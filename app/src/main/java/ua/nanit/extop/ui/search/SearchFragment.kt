package ua.nanit.extop.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.databinding.FragmentSearchBinding
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.ui.base.BaseFragment
import ua.nanit.extop.ui.shared.SharedViewModel

class SearchFragment : BaseFragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)
        sharedViewModel = sharedViewModel()

        binding.ratesCurrencyIn.setOnClickListener {
            navigation.navToCurrencies()
            viewModel.loadCurrencies(Direction.IN)
        }

        binding.ratesCurrencyOut.setOnClickListener {
            navigation.navToCurrencies()
            viewModel.loadCurrencies(Direction.OUT)
        }

        binding.searchBtnSwap.setOnClickListener(this::onSwapClicked)
        binding.searchBtnConfirm.setOnClickListener { viewModel.applySearchParams() }

        viewModel.currencyIn.observe(viewLifecycleOwner) {
            binding.ratesCurrencyIn.setText(it.name)
        }

        viewModel.currencyOut.observe(viewLifecycleOwner) {
            binding.ratesCurrencyOut.setText(it.name)
        }

        viewModel.enableConfirmBtn.observe(viewLifecycleOwner, this::observeEnableConfirmBtn)
        viewModel.search.observe(viewLifecycleOwner) { observeSearch() }
    }

    override fun onResume() {
        super.onResume()
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

    private fun observeEnableConfirmBtn(value: Boolean) {
        binding.searchBtnConfirm.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun observeSearch() {
        sharedViewModel.signalRefreshRates()
        navigation.navigateUp()
    }

    private fun onSwapClicked(view: View) {
        binding.searchBtnSwap.animate()
            .setDuration(300)
            .rotation(180f)
            .withEndAction { view.rotation = 0.0F }
            .setInterpolator(FastOutSlowInInterpolator())
            .start()

        viewModel.swapCurrencies()
    }

}