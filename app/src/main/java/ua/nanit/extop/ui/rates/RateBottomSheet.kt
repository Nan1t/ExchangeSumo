package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
    var isManual = false
    var isMediator = false
    var isCardVerify = false
    var linkClickListener: View.OnClickListener? = null
    var infoClickListener: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsheet_rate, container, false)
        val titleView = view.findViewById<TextView>(R.id.rate_dialog_title)
        val badgeManual = view.findViewById<LinearLayout>(R.id.rate_dialog_badge_manual)
        val badgeMediator = view.findViewById<LinearLayout>(R.id.rate_dialog_badge_mediator)
        val badgeCardVerify = view.findViewById<LinearLayout>(R.id.rate_dialog_badge_card)
        val btnLink = view.findViewById<View>(R.id.rate_dialog_link)
        val btnInfo = view.findViewById<View>(R.id.rate_dialog_info)

        titleView.text = title
        badgeManual.visibility = if (isManual) View.VISIBLE else View.GONE
        badgeMediator.visibility = if (isMediator) View.VISIBLE else View.GONE
        badgeCardVerify.visibility = if (isCardVerify) View.VISIBLE else View.GONE

        btnLink.setOnClickListener(linkClickListener)
        btnInfo.setOnClickListener(infoClickListener)

        return view
    }

    fun hide() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

}