package ua.nanit.extop.ui.doublex

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.DoubleExchange
import ua.nanit.extop.util.toRawString

class DoubleExchangeHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var exchange: DoubleExchange? = null

    private val amountIn = view.findViewById<TextView>(R.id.double_in_amount)
    private val amountOut = view.findViewById<TextView>(R.id.double_out_amount)
    private val amountTransit = view.findViewById<TextView>(R.id.double_transit_amount)
    private val currencyTransit = view.findViewById<TextView>(R.id.double_transit_currency)
    private val course = view.findViewById<TextView>(R.id.double_course)

    fun bind(exchange: DoubleExchange) {
        amountIn.text = exchange.amountIn.toRawString()
        amountOut.text = exchange.amountOut.toRawString()
        amountTransit.text = exchange.amountTransit.toRawString()
        currencyTransit.text = exchange.currencyTransit
        course.text = exchange.course.toString()
    }

    fun setOnClick(listener: (DoubleExchange) -> Unit) {
        itemView.setOnClickListener {
            if (exchange != null) {
                listener(exchange!!)
            }
        }
    }

    fun colorizeBackground(pos: Int) {
        val bgColor = if (pos %2 == 0) R.color.light else R.color.lighter
        itemView.setBackgroundResource(bgColor)
    }

}