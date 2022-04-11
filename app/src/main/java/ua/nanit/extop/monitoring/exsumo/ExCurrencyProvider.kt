package ua.nanit.extop.monitoring.exsumo

import com.google.gson.Gson
import ua.nanit.extop.monitoring.CurrencyProvider
import ua.nanit.extop.monitoring.data.Currency
import java.io.InputStream

/**
 * This "parser" reads predefined static currencies
 * because ExchangeSumo defines them statically in JS file
 */
class ExCurrencyProvider(
    input: InputStream
) : CurrencyProvider {

    private val gson = Gson()
    private val reader = input.reader(Charsets.UTF_8)
    private val list = gson.fromJson(reader, ExCurrencies::class.java)

    override fun provide(): List<Currency> {
        return list.currencies
    }

    data class ExCurrencies(
        val currencies: List<Currency>
    )

}