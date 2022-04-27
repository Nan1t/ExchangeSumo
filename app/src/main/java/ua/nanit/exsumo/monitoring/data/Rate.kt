package ua.nanit.exsumo.monitoring.data

data class Rate(
    val exchanger: String,
    var amountIn: Double,
    var amountOut: Double,
    var currencyIn: String,
    var currencyOut: String,
    val minAmount: Double,
    val fund: Double,
    val link: String,
    val reviewsLink: String,
    val isManual: Boolean,
    val isMediator: Boolean,
    val isCardVerify: Boolean,
    var active: Boolean = true,
    var inactiveByMinAmount: Boolean = false,
    var inactiveByFund: Boolean = false
) : Computed {

    override fun calcIn(amount: Double) {
        val course = amountOut / amountIn
        amountIn = amount
        amountOut = amount * course
        inactiveByMinAmount = amount < minAmount
        inactiveByFund = amountOut > fund
        active = !inactiveByMinAmount && !inactiveByFund
    }

    override fun calcOut(amount: Double) {
        val course = amountIn / amountOut
        amountOut = amount
        amountIn = amount * course
        inactiveByMinAmount = amountIn < minAmount
        inactiveByFund = amountOut > fund
        active = !inactiveByMinAmount && !inactiveByFund
    }
}