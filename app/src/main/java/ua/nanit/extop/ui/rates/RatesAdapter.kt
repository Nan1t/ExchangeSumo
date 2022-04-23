package ua.nanit.extop.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.nanit.extop.R
import ua.nanit.extop.databinding.ItemRateBinding
import ua.nanit.extop.monitoring.data.Rate
import ua.nanit.extop.ui.base.BaseRateAdapter

class RatesAdapter(
    clickListener: (Rate) -> Unit
) : BaseRateAdapter<Rate, RateHolder>(clickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRateBinding.inflate(inflater, parent, false)
        return RateHolder(binding)
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        val rate = list?.get(position) ?: return

        holder.bind(rate)
        holder.colorizeBackground(position)
        holder.setOnClick(rateClickListener)
    }

}