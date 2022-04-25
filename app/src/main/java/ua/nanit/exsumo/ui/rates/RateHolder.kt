package ua.nanit.exsumo.ui.rates

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exsumo.R
import ua.nanit.exsumo.databinding.ItemRateBinding
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.util.getColorFromAttr
import ua.nanit.exsumo.util.toRawString

class RateHolder(
    private val binding: ItemRateBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val DISABLED_OPACITY = 0.3F
    }

    private var rate: Rate? = null

    private val firstColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.backgroundColor)
    private val secondColor = itemView.context
        .getColorFromAttr(R.attr.backgroundColorVariant)
    private val errorColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.colorError)
    private val textColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.colorOnBackground)

    fun bind(rate: Rate) {
        this.rate = rate

        binding.rateOrganization.text = rate.exchanger
        binding.rateFund.text = rate.fund.toString()
        binding.rateMinAmount.text = rate.minAmount.toRawString()
        binding.rateAmountIn.text = rate.amountIn.toRawString()
        binding.rateAmountOut.text = rate.amountOut.toRawString()
        binding.rateCurrencyIn.text = rate.currencyIn
        binding.rateCurrencyOut.text = rate.currencyOut

        binding.rateIconManual.visibility = if (rate.isManual) View.VISIBLE else View.GONE
        binding.rateIconMediator.visibility = if (rate.isMediator) View.VISIBLE else View.GONE
        binding.rateIconCardVerify.visibility = if (rate.isCardVerify) View.VISIBLE else View.GONE

        binding.rateFund.setTextColor(textColor)
        binding.rateMinAmountText.setTextColor(textColor)
        binding.rateMinAmount.setTextColor(textColor)

        if (!rate.active) {
            binding.rateOrganization.alpha = DISABLED_OPACITY
            binding.rateFund.alpha = DISABLED_OPACITY
            binding.rateMinAmountText.alpha = DISABLED_OPACITY
            binding.rateMinAmount.alpha = DISABLED_OPACITY
            binding.rateAmountIn.alpha = DISABLED_OPACITY
            binding.rateAmountOut.alpha = DISABLED_OPACITY
            binding.rateCurrencyIn.alpha = DISABLED_OPACITY
            binding.rateCurrencyOut.alpha = DISABLED_OPACITY
            binding.rateIconManual.alpha = DISABLED_OPACITY
            binding.rateIconMediator.alpha = DISABLED_OPACITY
            binding.rateIconCardVerify.alpha = DISABLED_OPACITY

            if (rate.inactiveByFund) {
                binding.rateFund.alpha = 1.0F
                binding.rateFund.setTextColor(errorColor)
            }

            if (rate.inactiveByMinAmount) {
                binding.rateMinAmountText.alpha = 1.0F
                binding.rateMinAmount.alpha = 1.0F
                binding.rateMinAmountText.setTextColor(errorColor)
                binding.rateMinAmount.setTextColor(errorColor)
            }
        } else {
            binding.rateOrganization.alpha = 1.0F
            binding.rateFund.alpha = 1.0F
            binding.rateMinAmountText.alpha = 1.0F
            binding.rateMinAmount.alpha = 1.0F
            binding.rateAmountIn.alpha = 1.0F
            binding.rateAmountOut.alpha = 1.0F
            binding.rateCurrencyIn.alpha = 1.0F
            binding.rateCurrencyOut.alpha = 1.0F
            binding.rateIconManual.alpha = 1.0F
            binding.rateIconMediator.alpha = 1.0F
            binding.rateIconCardVerify.alpha = 1.0F
        }
    }

    fun setOnClick(listener: (Rate) -> Unit) {
        itemView.setOnClickListener {
            if (rate != null) {
                listener(rate!!)
            }
        }
    }

    fun colorizeBackground(pos: Int) {
        val bgColor = if (pos %2 == 0) firstColor else secondColor
        itemView.setBackgroundColor(bgColor)
    }

}