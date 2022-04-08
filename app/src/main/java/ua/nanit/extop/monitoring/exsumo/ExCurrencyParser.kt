package ua.nanit.extop.monitoring.exsumo

import com.google.gson.Gson
import ua.nanit.extop.monitoring.data.Currencies
import java.io.InputStream

/**
 * This "parser" reads predefined static currencies
 * because ExchangeSumo defines them statically in JS file
 */
class ExCurrencyParser(private val input: InputStream) {

    fun parse(): Currencies {
        val gson = Gson()
        return gson.fromJson(input.reader(Charsets.UTF_8), Currencies::class.java)
    }

}