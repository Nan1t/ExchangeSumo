package ua.nanit.exchange.storage

import ua.nanit.exchange.data.SearchOptions

interface RatesStorage {

    fun getCurrencyIn(): String?

    fun getCurrencyOut(): String?

    fun getAmountIn(): Int

    fun getAmountOut(): Int

    fun isCalcCommissions(): Boolean

    fun saveOptions(options: SearchOptions)

}