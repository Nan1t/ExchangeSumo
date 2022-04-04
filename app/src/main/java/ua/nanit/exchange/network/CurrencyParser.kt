package ua.nanit.exchange.network

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ua.nanit.exchange.data.Currency
import java.util.*

object CurrencyParser {

    private const val WM_URL = "https://kurs.com.ua/wm"

    fun parse(): List<Currency> {
        val doc = Jsoup.connect(WM_URL).get()
        val elements = doc.select(".valuta-list ul li[data-role=currency]")
        val currencies = LinkedList<Currency>()

        for (elem in elements) {
            val currency = parseElement(elem)
            currencies.add(currency)
        }

        return emptyList()
    }

    private fun parseElement(elem: Element): Currency {
        val id = elem.attr("data-id")
        val symbol = elem.attr("data-value")
        val imgUrl = elem.attr("data-image")
        val caption = elem.child(0).text()

        return Currency(id, symbol, caption, "https:$imgUrl")
    }

}