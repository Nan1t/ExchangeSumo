package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    val amountIn: Int,
    val amountOut: Int,
    val fund: Int,
    val link: String,
    val reviewsLink: String?,
)