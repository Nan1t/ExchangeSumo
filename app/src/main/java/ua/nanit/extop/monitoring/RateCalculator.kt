package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Rate

interface RateCalculator {

    fun calculate(rates: List<Rate>, dir: Direction, amount: Double)

}