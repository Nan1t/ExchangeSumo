package ua.nanit.exsumo.monitoring.impl

import com.google.gson.Gson
import ua.nanit.exsumo.monitoring.CurrencyRepo
import ua.nanit.exsumo.monitoring.data.Currency
import java.io.InputStream

/**
 * This "parser" reads predefined static currencies
 * because ExchangeSumo defines them statically in JS file
 */
class SumoCurrencyRepo(
    input: InputStream
) : CurrencyRepo {

    private val gson = Gson()
    private val reader = input.reader(Charsets.UTF_8)
    private val list = gson.fromJson(reader, ExCurrencies::class.java)
    private val currencies = list.currencies
    private val map = HashMap<String, Currency>()

    init {
        for (currency in currencies) {
            map[currency.id] = currency
        }
    }

    override fun provide(): List<Currency> {
        return currencies
    }

    override fun getCurrency(id: String): Currency? {
        return map[id]
    }

    data class ExCurrencies(
        val currencies: List<Currency>
    )

}