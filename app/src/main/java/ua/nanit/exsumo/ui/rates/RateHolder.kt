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

    private var rate: Rate? = null

    private val firstColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.backgroundColor)
    private val secondColor = itemView.context
        .getColorFromAttr(R.attr.backgroundColorVariant)

    fun bind(rate: Rate) {
        this.rate = rate

        binding.rateOrganization.text = rate.exchanger
        binding.rateFund.text = rate.fund.toString()
        binding.rateMinAmount.text = rate.minAmount.toString()
        binding.rateAmountIn.text = rate.amountIn.toRawString()
        binding.rateAmountOut.text = rate.amountOut.toRawString()
        binding.rateCurrencyIn.text = rate.currencyIn
        binding.rateCurrencyOut.text = rate.currencyOut

        binding.rateIconManual.visibility = if (rate.isManual) View.VISIBLE else View.GONE
        binding.rateIconMediator.visibility = if (rate.isMediator) View.VISIBLE else View.GONE
        binding.rateIconCardVerify.visibility = if (rate.isCardVerify) View.VISIBLE else View.GONE
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