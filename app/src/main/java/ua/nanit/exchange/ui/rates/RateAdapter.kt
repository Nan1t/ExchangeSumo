package ua.nanit.exchange.ui.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exchange.R
import ua.nanit.exchange.data.RateInfo

class RateAdapter : RecyclerView.Adapter<RateAdapter.Holder>() {

    val list = ArrayList<RateInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rates_table_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val value = list[position]
        holder.bind(value)

        if (position % 2 == 0) {
            holder.view.setBackgroundResource(R.color.light)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rate: RateInfo) {
            val textOrg = view.findViewById<TextView>(R.id.item_organization)
            val textFund = view.findViewById<TextView>(R.id.item_fund)
            val textFrom = view.findViewById<TextView>(R.id.item_amount_from)
            val textAmountGive = view.findViewById<TextView>(R.id.item_amount_give)
            val textAmountGet = view.findViewById<TextView>(R.id.item_amount_get)

            textOrg.text = rate.organization.title
            textFund.text = rate.value.amount.toString()
            textFrom.text = rate.value.minAmount
            textAmountGive.text = rate.value.`in`.toString()
            textAmountGet.text = rate.value.out.toString()
        }

    }

}