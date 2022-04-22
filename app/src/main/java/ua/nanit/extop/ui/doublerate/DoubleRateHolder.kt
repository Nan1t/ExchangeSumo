package ua.nanit.extop.ui.doublerate

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.DoubleRate
import ua.nanit.extop.util.getColorFromAttr
import ua.nanit.extop.util.toRawString

class DoubleRateHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var rate: DoubleRate? = null

    private val amountIn = view.findViewById<TextView>(R.id.double_in_amount)
    private val amountOut = view.findViewById<TextView>(R.id.double_out_amount)
    private val amountTransit = view.findViewById<TextView>(R.id.double_transit_amount)
    private val currencyIn = view.findViewById<TextView>(R.id.double_in_currency)
    private val currencyOut = view.findViewById<TextView>(R.id.double_out_currency)
    private val currencyTransit = view.findViewById<TextView>(R.id.double_transit_currency)
    private val course = view.findViewById<TextView>(R.id.double_course)

    private val firstColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.backgroundColor)
    private val secondColor = itemView.context
        .getColorFromAttr(R.attr.backgroundColorVariant)

    fun bind(rate: DoubleRate) {
        this.rate = rate

        amountIn.text = rate.amountIn.toRawString()
        amountOut.text = rate.amountOut.toRawString()
        amountTransit.text = rate.amountTransit.toRawString()
        currencyIn.text = rate.currencyIn
        currencyOut.text = rate.currencyOut
        currencyTransit.text = rate.currencyTransit
        course.text = rate.course.toString()
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