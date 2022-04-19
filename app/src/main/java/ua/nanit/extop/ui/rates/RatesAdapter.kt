package ua.nanit.extop.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Rate

class RatesAdapter(
    private val rateClickListener: (Rate) -> Unit
) : RecyclerView.Adapter<RateHolder>() {

    private var list: List<Rate>? = null

    fun update(rates: List<Rate>) {
        list = rates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false)
        return RateHolder(view)
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        val rate = list?.get(position) ?: return

        holder.bind(rate)
        holder.colorizeBackground(position)
        holder.setOnClick(rateClickListener)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}