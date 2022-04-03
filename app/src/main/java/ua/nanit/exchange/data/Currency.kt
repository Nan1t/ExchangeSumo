package ua.nanit.exchange.data

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("currency_id")
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String
)