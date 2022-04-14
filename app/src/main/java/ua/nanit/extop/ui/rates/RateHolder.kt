package ua.nanit.extop.ui.rates

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R

class RateHolder(view: View) : RecyclerView.ViewHolder(view) {

    val organization: TextView = view.findViewById(R.id.item_organization)
    val fund: TextView = view.findViewById(R.id.item_fund)
    val minAmount: TextView = view.findViewById(R.id.item_min_amount)
    val amountIn: TextView = view.findViewById(R.id.item_amount_in)
    val amountOut: TextView = view.findViewById(R.id.item_amount_out)

}