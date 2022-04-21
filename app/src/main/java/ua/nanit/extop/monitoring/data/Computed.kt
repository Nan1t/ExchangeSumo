package ua.nanit.extop.monitoring.data

interface Computed {

    fun calcIn(amount: Double)

    fun calcOut(amount: Double)

}