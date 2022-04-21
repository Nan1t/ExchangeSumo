package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Computed

class CommonCalculator : RateCalculator {

    override fun calculate(rates: List<Computed>, dir: Direction, amount: Double) {
        when (dir) {
            Direction.IN -> {
                rates.forEach { it.calcIn(amount) }
            }
            Direction.OUT -> {
                rates.forEach { it.calcOut(amount) }
            }
        }
    }
}