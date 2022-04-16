package ua.nanit.extop.ui.doublex

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import ua.nanit.extop.R
import ua.nanit.extop.ui.BaseFragment

class DoubleExchangeFragment : BaseFragment(R.layout.fragment_double) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}