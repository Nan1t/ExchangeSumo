package ua.nanit.extop.monitoring.data

data class DoubleExchange(
    val currencyIn: String,
    val currencyOut: String,
    val amountIn: Double,
    val amountOut: Double,
    val amountTransit: Double,
    val currencyTransit: String,
    val course: Double,
    val firstLink: String,
    val secondLink: String,
    val firstExchanger: String,
    val secondExchanger: String
)