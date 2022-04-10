package ua.nanit.extop.ui.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Currency

class CurrencyAdapter(
    private val clickListener: () -> Unit
) : RecyclerView.Adapter<CurrencyHolder>() {

    private lateinit var recyclerView: RecyclerView
    private var list = ArrayList<Currency>()
    private var selectedIndex: Int = -1

    var selected: Currency? = null

    fun update(currencies: List<Currency>) {
        this.list.clear()
        this.list.addAll(currencies)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)

        return CurrencyHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val currency = list[position]

        holder.currencyId.text = currency.id
        holder.currencyName.text = currency.name

        if (holder.adapterPosition == selectedIndex) {
            holder.setSelectedColor()
        } else {
            holder.clearColor(position)
        }

        holder.itemView.setOnClickListener {
            if (selectedIndex != -1) {
                findHolder(selectedIndex)?.clearColor(selectedIndex)
            }

            selectedIndex = holder.adapterPosition
            selected = currency
            holder.setSelectedColor()
            clickListener()
        }
    }

    private fun findHolder(pos: Int): CurrencyHolder? {
        return recyclerView.findViewHolderForAdapterPosition(pos)
                as? CurrencyHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }
}