package ua.nanit.exsumo.monitoring.data

data class DoubleRate(
    val currencyIn: String,
    val currencyOut: String,
    var amountIn: Double,
    var amountOut: Double,
    val amountTransit: Double,
    val currencyTransit: String,
    val course: Double,
    val firstLink: String,
    val secondLink: String,
    val firstExchanger: String,
    val secondExchanger: String
) : Computed {

    override fun calcIn(amount: Double) {
        val course = amountOut / amountIn
        amountIn = amount
        amountOut = amount * course
    }

    override fun calcOut(amount: Double) {
        val course = amountIn / amountOut
        amountOut = amount
        amountIn = amount * course
    }
}