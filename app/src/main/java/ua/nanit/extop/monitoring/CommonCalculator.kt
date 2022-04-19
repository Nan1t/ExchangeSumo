package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Rate

class CommonCalculator : RateCalculator {

    override fun calculate(rates: List<Rate>, dir: Direction, amount: Double) {
        when (dir) {
            Direction.IN -> {
                calcIn(rates, amount)
            }
            Direction.OUT -> {
                calcOut(rates, amount)
            }
        }
    }

    private fun calcIn(rates: List<Rate>, amount: Double) {
        for (rate in rates) {
            val course = rate.amountOut / rate.amountIn

            rate.amountIn = amount
            rate.amountOut = amount * course
            rate.active = amount <= rate.fund
        }
    }

    private fun calcOut(rates: List<Rate>, amount: Double) {
        for (rate in rates) {
            val course = rate.amountIn / rate.amountOut

            rate.amountOut = amount
            rate.amountIn = amount * course
            rate.active = amount <= rate.fund
        }
    }
}