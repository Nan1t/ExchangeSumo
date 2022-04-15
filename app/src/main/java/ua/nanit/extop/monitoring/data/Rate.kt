package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    val amountIn: Float,
    val amountOut: Float,
    val minAmount: Float,
    val fund: Float,
    val link: String,
    val reviewsLink: String?,
)