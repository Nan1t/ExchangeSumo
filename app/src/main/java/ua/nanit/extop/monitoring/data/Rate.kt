package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    val amountIn: Float,
    val amountOut: Float,
    val minAmount: Int,
    val fund: Int,
    val link: String,
    val reviewsLink: String,
    val isManual: Boolean,
    val isMediator: Boolean
)