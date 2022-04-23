package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.Computed

interface RateCalculator {

    fun calculate(rates: List<Computed>, dir: Direction, amount: Double)

}