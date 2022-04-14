package ua.nanit.extop.ui.dexchange

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import ua.nanit.extop.R
import ua.nanit.extop.ui.requireCompatActionBar

class DoubleExchangeFragment : Fragment(R.layout.fragment_double) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        requireCompatActionBar()?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}