package ua.nanit.extop.ui.search

import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.databinding.ItemCurrencyBinding
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.util.getColorFromAttr

class CurrencyHolder(
    private val binding: ItemCurrencyBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val firstColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.backgroundColor)
    private val secondColor = itemView.context
        .getColorFromAttr(R.attr.backgroundColorVariant)
    private val selectedColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.colorPrimary)

    private val textDefaultColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.colorOnBackground)
    private val textSelectedColor = itemView.context
        .getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary)

    fun bind(currency: Currency) {
        binding.itemCurrencyId.text = currency.id
        binding.itemCurrencyName.text = currency.name
    }

    fun clearColor(pos: Int) {
        if (pos % 2 == 0) {
            itemView.setBackgroundColor(firstColor)
        } else {
            itemView.setBackgroundColor(secondColor)
        }

        binding.itemCurrencyId.setTextColor(textDefaultColor)
        binding.itemCurrencyName.setTextColor(textDefaultColor)
    }

    fun setSelectedColor() {
        binding.itemCurrencyId.setTextColor(textSelectedColor)
        binding.itemCurrencyName.setTextColor(textSelectedColor)
        itemView.setBackgroundColor(selectedColor)
    }

}