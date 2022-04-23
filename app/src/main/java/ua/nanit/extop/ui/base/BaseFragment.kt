package ua.nanit.extop.ui.base

import android.content.Context
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.ui.Navigation
import ua.nanit.extop.ui.shared.SharedViewModel

abstract class BaseFragment : Fragment() {

    protected lateinit var navigation: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    fun sharedViewModel(): SharedViewModel =
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    fun Fragment.requireCompatActionBar() : ActionBar? {
        val activity = requireActivity() as AppCompatActivity
        return activity.supportActionBar
    }
}