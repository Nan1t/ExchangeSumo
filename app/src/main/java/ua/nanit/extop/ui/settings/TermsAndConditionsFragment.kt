package ua.nanit.extop.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ua.nanit.extop.R
import ua.nanit.extop.databinding.FragmentTermsBinding

class TermsAndConditionsFragment : Fragment(R.layout.fragment_terms) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTermsBinding.bind(view)

        binding.tosText.text = view.context.resources
            .openRawResource(R.raw.terms_and_conditions)
            .reader()
            .readText()
    }

}