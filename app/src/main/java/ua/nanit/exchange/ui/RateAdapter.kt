package ua.nanit.exchange.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exchange.R
import ua.nanit.exchange.data.RateValue

class RateAdapter(private val list: List<RateValue>) : RecyclerView.Adapter<RateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_table_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val value = list[position]
        holder.bind(value)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rate: RateValue) {
            val textOrg = view.findViewById<TextView>(R.id.item_organization)
            val textFund = view.findViewById<TextView>(R.id.item_fund)
            val textFrom = view.findViewById<TextView>(R.id.item_amount_from)
            val textAmountGive = view.findViewById<TextView>(R.id.item_amount_give)
            val textAmountGet = view.findViewById<TextView>(R.id.item_amount_get)

            textOrg.text = rate.organizationId
            textFund.text = rate.amount.toString()
            textFrom.text = rate.minAmount
            textAmountGive.text = rate.`in`.toString()
            textAmountGet.text = rate.out.toString()
        }

    }

}