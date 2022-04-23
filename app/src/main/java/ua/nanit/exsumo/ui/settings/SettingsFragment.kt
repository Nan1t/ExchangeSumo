package ua.nanit.exsumo.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import ua.nanit.exsumo.R
import ua.nanit.exsumo.storage.AppStorage
import ua.nanit.exsumo.ui.base.BasePrefsFragment
import ua.nanit.exsumo.util.ThemeUtil
import java.util.*

class SettingsFragment : BasePrefsFragment(R.xml.preferences) {

    private lateinit var storage: AppStorage
    private lateinit var locale: ListPreference
    private lateinit var nightMode: SwitchPreferenceCompat
    private lateinit var about: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)

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