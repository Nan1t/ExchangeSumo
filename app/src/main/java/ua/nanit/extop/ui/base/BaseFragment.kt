package ua.nanit.extop.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.nanit.extop.ui.Navigation
import ua.nanit.extop.ui.shared.SharedViewModel

open class BaseFragment(private val layoutId: Int) : Fragment() {

    protected lateinit var navigation: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun sharedViewModel(): SharedViewModel =
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    fun Fragment.requireCompatActionBar() : ActionBar? {
        val activity = requireActivity() as AppCompatActivity
        return activity.supportActionBar
    }
}