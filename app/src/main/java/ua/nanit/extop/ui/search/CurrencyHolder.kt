package ua.nanit.extop.ui.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.util.getColorFromAttr

class CurrencyHolder(view: View) : RecyclerView.ViewHolder(view) {

    val currencyId: TextView = view.findViewById(R.id.item_currency_id)
    val currencyName: TextView = view.findViewById(R.id.item_currency_name)

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

    fun clearColor(pos: Int) {
        if (pos % 2 == 0) {
            itemView.setBackgroundColor(firstColor)
        } else {
            itemView.setBackgroundColor(secondColor)
        }

        currencyId.setTextColor(textDefaultColor)
        currencyName.setTextColor(textDefaultColor)
    }

    fun setSelectedColor() {
        currencyId.setTextColor(textSelectedColor)
        currencyName.setTextColor(textSelectedColor)
        itemView.setBackgroundColor(selectedColor)
    }

}