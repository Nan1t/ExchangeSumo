package ua.nanit.extop.ui.exchanger

import android.os.Bundle
import android.view.View
import ua.nanit.extop.R
import ua.nanit.extop.ui.BaseFragment

class ExchangerFragment : BaseFragment(R.layout.fragment_exchanger) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

}