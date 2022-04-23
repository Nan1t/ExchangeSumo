package ua.nanit.exsumo.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import ua.nanit.exsumo.R
import ua.nanit.exsumo.ui.Navigation
import ua.nanit.exsumo.ui.base.BasePrefsFragment

class AboutFragment : BasePrefsFragment(R.xml.about) {

    private lateinit var contacts: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)

        val ctx = requireContext()
        val info = ctx.packageManager.getPackageInfo(ctx.packageName, 0)

        val version = findPreference<Preference>("version")!!
        val tos = findPreference<Preference>("tos")!!
        val sources = findPreference<Preference>("sources")!!
        contacts = findPreference("contacts")!!

        version.summary = info.versionName

        contacts.setOnPreferenceClickListener {
            copyContact()
            false
        }

        tos.setOnPreferenceClickListener {
            navigation.navToTermsAndConditions()
            false
        }

        sources.setOnPreferenceClickListener {
            openSources()
            false
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

    private fun copyContact() {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        val clipData = ClipData.newPlainText("email", contacts.summary)

        clipboardManager.setPrimaryClip(clipData)

        Snackbar.make(
            requireView(),
            R.string.about_contacts_copied,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun openSources() {
        val uri = Uri.parse(requireContext().getString(R.string.about_source_url))
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}