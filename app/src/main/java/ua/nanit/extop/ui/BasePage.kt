package ua.nanit.extop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

open class BasePage(layoutId: Int) : Fragment(layoutId) {

    protected lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel = ViewModelProvider(activity, MainVmFactory(activity))
            .get(MainViewModel::class.java)
    }

}