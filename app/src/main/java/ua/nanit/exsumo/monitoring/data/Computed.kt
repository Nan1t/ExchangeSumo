package ua.nanit.exsumo.monitoring.data

interface Computed {

    fun calcIn(amount: Double)

    fun calcOut(amount: Double)

}