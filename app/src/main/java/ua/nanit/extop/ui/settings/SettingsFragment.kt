package ua.nanit.extop.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import ua.nanit.extop.R
import ua.nanit.extop.storage.AppStorage
import ua.nanit.extop.ui.Navigation
import ua.nanit.extop.util.ThemeUtil
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var navigation: Navigation

    private lateinit var storage: AppStorage
    private lateinit var locale: ListPreference
    private lateinit var nightMode: SwitchPreferenceCompat
    private lateinit var about: Preference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        storage = AppStorage(requireContext())

        locale = findPreference(AppStorage.KEY_LOCALE)!!
        nightMode = findPreference(AppStorage.KEY_NIGHT_MODE)!!
        about = findPreference("about")!!

        locale.value = Locale.getDefault().language

        locale.setOnPreferenceChangeListener(::onLocaleChange)
        nightMode.setOnPreferenceChangeListener(::onThemeChange)

        about.setOnPreferenceClickListener {
            navigation.navToAbout()
            true
        }
    }

    private fun onLocaleChange(pref: Preference, newVal: Any?): Boolean {
        if (pref is ListPreference && newVal is String) {
            requireActivity().recreate()
            return true
        }
        return false
    }

    private fun onThemeChange(pref: Preference, nightMode: Any?): Boolean {
        if (pref is SwitchPreferenceCompat && nightMode is Boolean) {
            ThemeUtil.setNightMode(nightMode)
            return true
        }
        return false
    }

}