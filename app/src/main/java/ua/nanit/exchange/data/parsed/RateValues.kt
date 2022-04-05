package ua.nanit.exchange.data.parsed

data class RateValues(val values: Values) {

    data class Values(
        val new: List<RateValue>,
        val old: List<RateValue>,
        val count: Int = 0
    )
}