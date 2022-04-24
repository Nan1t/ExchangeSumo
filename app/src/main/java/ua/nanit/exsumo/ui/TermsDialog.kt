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

        val builder = MaterialAlertDialogBuilder(ctx)
        builder.setTitle(R.string.about_terms)
        builder.setMessage(text)
        builder.setPositiveButton(R.string.ok) { _, _ ->
            storage?.acceptTerms()
        }
        if (storage != null) {
            builder.setNegativeButton(R.string.cancel) { _, _ ->
                exitProcess(0)
            }
        }
        builder.create().show()
    }

}