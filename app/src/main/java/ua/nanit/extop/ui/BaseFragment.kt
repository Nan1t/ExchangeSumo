package ua.nanit.extop.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

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

}