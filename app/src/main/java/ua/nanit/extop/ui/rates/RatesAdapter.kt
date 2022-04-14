package ua.nanit.extop.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Rate

class RatesAdapter : RecyclerView.Adapter<RateHolder>() {

    private val list = ArrayList<Rate>()

    fun update(rates: List<Rate>) {
        list.clear()
        list.addAll(rates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false)
        return RateHolder(view)
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        val rate = list[position]

        holder.organization.text = rate.exchanger
        holder.fund.text = rate.fund.toString()
        holder.minAmount.text = rate.minAmount.toString()
        holder.amountIn.text = rate.amountIn.toString()
        holder.amountOut.text = rate.amountOut.toString()

        val bgColor = if (position %2 == 0) R.color.light else R.color.lighter
        holder.itemView.setBackgroundResource(bgColor)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}