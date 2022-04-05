package ua.nanit.exchange.data

data class SearchOptions(
    val currencyIn: String?,
    val currencyOut: String?,
    val amountIn: Int,
    val amountOut: Int,
    val calcCommissions: Boolean
)