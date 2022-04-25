package ua.nanit.exsumo.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.nanit.exsumo.R
import ua.nanit.exsumo.storage.AppStorage
import kotlin.system.exitProcess

class TermsDialog {

    fun show(ctx: Context, storage: AppStorage? = null) {
        val builder = MaterialAlertDialogBuilder(ctx)
        builder.setTitle(R.string.about_terms)
        builder.setMessage(R.string.terms_text)
        builder.setPositiveButton(R.string.terms_ok) { _, _ ->
            storage?.acceptTerms()
        }
        builder.setNegativeButton(R.string.terms_website) { _, _ ->
            val url = ctx.resources.getString(R.string.about_partner_url)
            ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            exitProcess(0)
        }
        builder.create().show()
    }

}