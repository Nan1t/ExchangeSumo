package ua.nanit.exchange.data

data class RatesInfo(
    var values: RateValues,
    var organizations: Map<String, Organization>,
    var currencies: Map<String, Currency>,
)