package ua.nanit.exchange.data

import com.google.gson.annotations.SerializedName

data class Organization(
    @SerializedName("organization_id")
    val id: String,
    val title: String,
    val website: String,
)