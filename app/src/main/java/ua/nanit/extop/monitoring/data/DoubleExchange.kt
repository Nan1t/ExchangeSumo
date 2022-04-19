package ua.nanit.extop.monitoring.data

data class DoubleExchange(
    val amountIn: Double,
    val amountOut: Double,
    val amountTransit: Double,
    val currencyTransit: String,
    val course: Double,
    val firstLink: String,
    val secondLink: String,
)