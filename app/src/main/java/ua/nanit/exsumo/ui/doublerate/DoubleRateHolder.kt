package ua.nanit.exsumo.ui.doublerate

import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exsumo.R
import ua.nanit.exsumo.databinding.ItemDoubleRateBinding
import ua.nanit.exsumo.monitoring.data.DoubleRate
import ua.nanit.exsumo.util.getColorFromAttr
import ua.nanit.exsumo.util.toRawString

class DoubleRateHolder(
    private val binding: ItemDoubleRateBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var rate: DoubleRate? = null

    private val firstColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.backgroundColor)
    private val secondColor = itemView.context
        .getColorFromAttr(R.attr.backgroundColorVariant)

    fun bind(rate: DoubleRate) {
        this.rate = rate

        binding.doubleInAmount.text = rate.amountIn.toRawString()
        binding.doubleOutAmount.text = rate.amountOut.toRawString()
        binding.doubleTransitAmount.text = rate.amountTransit.toRawString()
        binding.doubleInCurrency.text = rate.currencyIn
        binding.doubleOutCurrency.text = rate.currencyOut
        binding.doubleTransitCurrency.text = rate.currencyTransit
        binding.doubleCourse.text = rate.course.toString()
    }

    fun setOnClick(listener: (DoubleRate) -> Unit) {
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