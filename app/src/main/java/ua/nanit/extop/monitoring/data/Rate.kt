package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    var amountIn: Double,
    var amountOut: Double,
    val minAmount: Int,
    val fund: Int,
    val link: String,
    val reviewsLink: String,
    val isManual: Boolean,
    val isMediator: Boolean,
    val isCardVerify: Boolean,
    var active: Boolean = true
)