package ua.nanit.extop.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ua.nanit.extop.R
import ua.nanit.extop.requireCompatActionBar

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireCompatActionBar()?.hide()
    }

}