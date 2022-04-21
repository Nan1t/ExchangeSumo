package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    var amountIn: Double,
    var amountOut: Double,
    var currencyIn: String,
    var currencyOut: String,
    val minAmount: Int,
    val fund: Int,
    val link: String,
    val reviewsLink: String,
    val isManual: Boolean,
    val isMediator: Boolean,
    val isCardVerify: Boolean,
    var active: Boolean = true
) : Computed {

    override fun calcIn(amount: Double) {
        val course = amountOut / amountIn
        amountIn = amount
        amountOut = amount * course
        active = amount <= fund
    }

    override fun calcOut(amount: Double) {
        val course = amountIn / amountOut
        amountOut = amount
        amountIn = amount * course
        active = amount <= fund
    }
}