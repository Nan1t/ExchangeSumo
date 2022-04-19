package ua.nanit.extop.ui

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRateAdapter<T, A: RecyclerView.ViewHolder>(
    protected val rateClickListener: (T) -> Unit
) : RecyclerView.Adapter<A>() {

    protected var list: List<T>? = null

    fun update(list: List<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0
}