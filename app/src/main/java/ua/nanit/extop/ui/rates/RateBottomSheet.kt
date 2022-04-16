package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ua.nanit.extop.R

class RateBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "RateBottomSheet"
    }

    var title: String = ""
    var linkClickListener: View.OnClickListener? = null
    var infoClickListener: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_rate, container, false)
        val titleView = view.findViewById<TextView>(R.id.rate_dialog_title)
        val btnLink = view.findViewById<View>(R.id.rate_dialog_link)
        val btnInfo = view.findViewById<View>(R.id.rate_dialog_info)

        btnLink.setOnClickListener(linkClickListener)
        btnInfo.setOnClickListener(infoClickListener)
        titleView.text = title

        return view
    }

    fun hide() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

}