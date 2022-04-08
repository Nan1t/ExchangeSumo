package ua.nanit.extop.ui.rates

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R

class RateHolder(view: View) : RecyclerView.ViewHolder(view) {

    val organization = view.findViewById<TextView>(R.id.item_organization)
    val fund = view.findViewById<TextView>(R.id.item_fund)
    val minAmount = view.findViewById<TextView>(R.id.item_min_amount)
    val amountIn = view.findViewById<TextView>(R.id.item_amount_in)
    val amountOut = view.findViewById<TextView>(R.id.item_amount_out)

}