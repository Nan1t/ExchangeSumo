package ua.nanit.exsumo.ui.exchanger

import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exsumo.databinding.ItemReviewBinding
import ua.nanit.exsumo.monitoring.data.Review

class ReviewViewHolder(
    private val binding: ItemReviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(review: Review) {
        binding.itemReviewUsername.text = review.username
        binding.itemReviewDate.text = review.date
        binding.itemReviewText.text = review.text
    }

}