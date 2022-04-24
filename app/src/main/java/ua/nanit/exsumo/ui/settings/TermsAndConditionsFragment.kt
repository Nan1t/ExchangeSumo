package ua.nanit.exsumo.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.nanit.exsumo.R
import ua.nanit.exsumo.databinding.FragmentTermsBinding
import ua.nanit.exsumo.ui.base.BaseFragment

class TermsAndConditionsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTermsBinding.inflate(inflater, container, false)

        binding.tosText.text = binding.root.context.resources
            .openRawResource(R.raw.terms_and_conditions)
            .reader()
            .readText()

        return binding.root
    }

}