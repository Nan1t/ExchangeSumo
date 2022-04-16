package ua.nanit.extop.ui.rates

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Rate

class RateHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var rate: Rate? = null

    private val organization: TextView = view.findViewById(R.id.item_organization)
    private val fund: TextView = view.findViewById(R.id.item_fund)
    private val minAmount: TextView = view.findViewById(R.id.item_min_amount)
    private val amountIn: TextView = view.findViewById(R.id.item_amount_in)
    private val amountOut: TextView = view.findViewById(R.id.item_amount_out)

    fun bind(rate: Rate) {
        this.rate = rate

        organization.text = rate.exchanger
        fund.text = rate.fund.toString()
        minAmount.text = rate.minAmount.toString()
        amountIn.text = rate.amountIn.toString()
        amountOut.text = rate.amountOut.toString()
    }

    fun setOnClick(listener: (Rate) -> Unit) {
        itemView.setOnClickListener {
            if (rate != null) {
                listener(rate!!)
            }
        }
    }

    fun colorizeBackground(pos: Int) {
        val bgColor = if (pos %2 == 0) R.color.light else R.color.lighter
        itemView.setBackgroundResource(bgColor)
    }

}