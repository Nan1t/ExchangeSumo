package ua.nanit.exchange.data

import com.google.gson.annotations.SerializedName

data class RateValue(
    @SerializedName("organization_id")
    val organizationId: String,
    val `in`: Float,
    val out: Float,
    val amount: Float,
    @SerializedName("minamount")
    val minAmount: String,
)