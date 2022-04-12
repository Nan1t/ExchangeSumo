package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.nanit.extop.R
import ua.nanit.extop.requireCompatActionBar

class RatesFragment : Fragment(R.layout.fragment_rates) {

    private lateinit var ratesList: RecyclerView
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        requireCompatActionBar()?.show()

        ratesAdapter = RatesAdapter()
        ratesList = view.findViewById(R.id.rates_list)
        swipeRefresh = view.findViewById(R.id.rates_swipe_refresh)

        ratesList.adapter = ratesAdapter
        ratesList.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}