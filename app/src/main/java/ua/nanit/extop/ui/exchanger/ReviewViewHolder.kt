package ua.nanit.extop.ui.exchanger

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Review

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val username = view.findViewById<TextView>(R.id.item_review_username)
    private val date = view.findViewById<TextView>(R.id.item_review_date)
    private val content = view.findViewById<TextView>(R.id.item_review_text)

    fun bind(review: Review) {
        username.text = review.username
        date.text = review.date
        content.text = review.text
    }

}