package ua.nanit.exsumo.ui.base

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ua.nanit.exsumo.ui.Navigation

open class BasePrefsFragment(
    private val resId: Int
) : PreferenceFragmentCompat() {

    protected lateinit var navigation: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(resId, rootKey)
    }

}