package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Computed

interface RateCalculator {

    fun calculate(rates: List<Computed>, dir: Direction, amount: Double)

}