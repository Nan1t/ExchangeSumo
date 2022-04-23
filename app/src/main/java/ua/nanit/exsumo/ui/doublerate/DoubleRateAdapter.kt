package ua.nanit.exsumo.ui.doublerate

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.nanit.exsumo.databinding.ItemDoubleRateBinding
import ua.nanit.exsumo.monitoring.data.DoubleRate
import ua.nanit.exsumo.ui.base.BaseRateAdapter

class DoubleRateAdapter(
    clickListener: (DoubleRate) -> Unit
) : BaseRateAdapter<DoubleRate, DoubleRateHolder>(clickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoubleRateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDoubleRateBinding.inflate(inflater, parent, false)
        return DoubleRateHolder(binding)
    }

    override fun onBindViewHolder(holder: DoubleRateHolder, position: Int) {
        val rate = list?.get(position) ?: return

        holder.bind(rate)
        holder.setOnClick(rateClickListener)
        holder.colorizeBackground(position)
    }

}