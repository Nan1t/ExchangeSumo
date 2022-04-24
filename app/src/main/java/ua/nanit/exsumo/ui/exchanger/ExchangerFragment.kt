package ua.nanit.exsumo.ui.exchanger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ua.nanit.exsumo.databinding.FragmentExchangerBinding
import ua.nanit.exsumo.monitoring.data.Exchanger
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.ui.base.BaseFragment
import ua.nanit.exsumo.ui.shared.SharedViewModel

class ExchangerFragment : BaseFragment() {

    private lateinit var viewModel: ExchangerViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentExchangerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), ExchangerVmFactory(requireContext()))
            .get(ExchangerViewModel::class.java)
        sharedViewModel = sharedViewModel()

        binding.exchangerReviewsList.layoutManager = LinearLayoutManager(requireContext())

        binding.exchangerBtnWebsite.setOnClickListener { viewModel.openWebsite() }
        binding.exchangerBtnReviews.setOnClickListener { viewModel.openReviews() }

        viewModel.exchanger.observe(viewLifecycleOwner, this::observeExchanger)
        viewModel.url.observe(viewLifecycleOwner, this::observeUrl)
        sharedViewModel.rateInfo.observe(viewLifecycleOwner, this::requestInfo)
    }

    private fun requestInfo(rate: Rate) {
        viewModel.load(rate)
    }

    private fun observeUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun observeExchanger(exchanger: Exchanger) {
        binding.exchangerName.text = exchanger.name
        binding.exchangerReviewsList.adapter = ReviewsAdapter(exchanger.reviews)
        binding.exchangerStatus.text = exchanger.status
        binding.exchangerFund.text = exchanger.fund
        binding.exchangerAge.text = exchanger.age
        binding.exchangerCountry.text = exchanger.country

        Picasso.get()
            .load(Uri.parse(exchanger.iconUrl))
            .into(binding.exchangerLogo)
    }
}