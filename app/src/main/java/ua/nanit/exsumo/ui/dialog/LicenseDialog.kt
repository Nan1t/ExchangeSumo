package ua.nanit.exsumo.ui.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.nanit.exsumo.R

class LicenseDialog {

    fun show(ctx: Context) {
        val content = ctx.resources.openRawResource(R.raw.license)
            .bufferedReader()
            .readText()

        val builder = MaterialAlertDialogBuilder(ctx)
        builder.setTitle(R.string.about_license)
        builder.setMessage(content)
        builder.setPositiveButton(R.string.terms_ok) { _, _ -> }
        builder.create().show()
    }

}