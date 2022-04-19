package ua.nanit.extop.ui.doublex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.DoubleExchange
import ua.nanit.extop.ui.BaseRateAdapter

class DoubleExchangeAdapter(
    clickListener: (DoubleExchange) -> Unit
) : BaseRateAdapter<DoubleExchange, DoubleExchangeHolder>(clickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoubleExchangeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_double_exchange, parent, false)
        return DoubleExchangeHolder(view)
    }

    override fun onBindViewHolder(holder: DoubleExchangeHolder, position: Int) {
        val rate = list?.get(position) ?: return

        holder.bind(rate)
        holder.setOnClick(rateClickListener)
        holder.colorizeBackground(position)
    }

}