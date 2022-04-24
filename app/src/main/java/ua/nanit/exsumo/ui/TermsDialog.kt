package ua.nanit.exsumo.ui

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.nanit.exsumo.R
import ua.nanit.exsumo.storage.AppStorage
import kotlin.system.exitProcess

class TermsDialog {

    fun show(ctx: Context, storage: AppStorage? = null) {
        val text = ctx.resources
            .openRawResource(R.raw.terms_and_conditions)
            .reader()
            .readText()

        MaterialAlertDialogBuilder(ctx)
            .setTitle(R.string.about_terms)
            .setMessage(text)
            .setPositiveButton(R.string.ok) { _, _ ->
                storage?.acceptTerms()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                exitProcess(0)
            }
            .create()
            .show()
    }

}