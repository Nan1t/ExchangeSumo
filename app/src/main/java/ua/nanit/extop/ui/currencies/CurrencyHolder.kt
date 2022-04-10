package ua.nanit.extop.ui.currencies

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R

class CurrencyHolder(view: View) : RecyclerView.ViewHolder(view) {

    val currencyId: TextView = view.findViewById(R.id.item_currency_id)
    val currencyName: TextView = view.findViewById(R.id.item_currency_name)

    fun clearColor(pos: Int) {
        if (pos % 2 == 0) {
            itemView.setBackgroundResource(R.color.lighter)
        } else {
            itemView.setBackgroundResource(R.color.light)
        }

        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        currencyId.setTextColor(textColor)
        currencyName.setTextColor(textColor)
    }

    fun setSelectedColor() {
        val textColor = ContextCompat.getColor(itemView.context, R.color.lighter)
        currencyId.setTextColor(textColor)
        currencyName.setTextColor(textColor)
        itemView.setBackgroundResource(R.color.primaryColor)
    }

}