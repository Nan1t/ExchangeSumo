package ua.nanit.extop.ui.exchanger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.BaseFragment
import ua.nanit.extop.ui.shared.SharedViewModel

class ExchangerFragment : BaseFragment(R.layout.fragment_exchanger) {

    private lateinit var viewModel: ExchangerViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var exchangerLogo: ImageView
    private lateinit var exchangerName: TextView
    private lateinit var btnWebsite: FloatingActionButton
    private lateinit var btnReviews: FloatingActionButton
    private lateinit var reviewsList: RecyclerView

    private lateinit var aboutStatus: TextView
    private lateinit var aboutFund: TextView
    private lateinit var aboutAge: TextView
    private lateinit var aboutCountry: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), ExchangerVmFactory(requireContext()))
            .get(ExchangerViewModel::class.java)
        sharedViewModel = sharedViewModel()

        exchangerLogo = view.findViewById(R.id.exchanger_logo)
        exchangerName = view.findViewById(R.id.exchanger_name)
        aboutStatus = view.findViewById(R.id.exchanger_status)
        aboutFund = view.findViewById(R.id.exchanger_fund)
        aboutAge = view.findViewById(R.id.exchanger_age)
        aboutCountry = view.findViewById(R.id.exchanger_country)
        btnWebsite = view.findViewById(R.id.exchanger_btn_website)
        btnReviews = view.findViewById(R.id.exchanger_btn_reviews)
        reviewsList = view.findViewById(R.id.exchanger_reviews_list)
        reviewsList.layoutManager = LinearLayoutManager(requireContext())

        btnWebsite.setOnClickListener { viewModel.openWebsite() }
        btnReviews.setOnClickListener { viewModel.openReviews() }

        viewModel.exchanger.observe(viewLifecycleOwner, this::observeExchanger)
        viewModel.url.observe(viewLifecycleOwner, this::observeUrl)
        sharedViewModel.rateInfo.observe(viewLifecycleOwner, this::requestInfo)
    }

    override fun onResume() {
        super.onResume()
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

    private fun requestInfo(rate: Rate) {
        viewModel.load(rate)
    }

    private fun observeUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun observeExchanger(exchanger: Exchanger) {
        exchangerName.text = exchanger.name
        reviewsList.adapter = ReviewsAdapter(exchanger.reviews)
        aboutStatus.text = exchanger.status
        aboutFund.text = exchanger.fund
        aboutAge.text = exchanger.age
        aboutCountry.text = exchanger.country

        Picasso.get()
            .load(Uri.parse(exchanger.iconUrl))
            .into(exchangerLogo)
    }
}