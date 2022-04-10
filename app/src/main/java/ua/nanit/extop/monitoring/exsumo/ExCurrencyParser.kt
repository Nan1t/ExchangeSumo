package ua.nanit.extop.monitoring.exsumo

import com.google.gson.Gson
import ua.nanit.extop.monitoring.data.Currency
import java.io.InputStream

/**
 * This "parser" reads predefined static currencies
 * because ExchangeSumo defines them statically in JS file
 */
class ExCurrencyParser(private val input: InputStream) {

    fun parse(): List<Currency> {
        val gson = Gson()
        val list = gson.fromJson(input.reader(Charsets.UTF_8), ExCurrencies::class.java)
        return list.currencies
    }

    data class ExCurrencies(
        val currencies: List<Currency>
    )

}