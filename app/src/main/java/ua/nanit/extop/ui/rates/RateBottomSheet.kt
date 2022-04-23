package ua.nanit.extop.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.nanit.extop.databinding.BsheetRateBinding
import ua.nanit.extop.ui.base.BaseBottomSheet

class RateBottomSheet : BaseBottomSheet() {

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
    ): View {
        val binding = BsheetRateBinding.inflate(inflater, container, false)

        binding.rateDialogTitle.text = title
        binding.rateDialogBadgeManual.visibility = if (isManual) View.VISIBLE else View.GONE
        binding.rateDialogBadgeMediator.visibility = if (isMediator) View.VISIBLE else View.GONE
        binding.rateDialogBadgeCard.visibility = if (isCardVerify) View.VISIBLE else View.GONE

        binding.rateDialogLink.setOnClickListener(linkClickListener)
        binding.rateDialogInfo.setOnClickListener(infoClickListener)

        return binding.root
    }

    override fun tag(): String {
        return TAG
    }

}