package ua.nanit.exsumo.monitoring.impl

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ua.nanit.exsumo.monitoring.DoubleExchangeRepo
import ua.nanit.exsumo.monitoring.data.DoubleRate

class SumoDoubleExchangeRepo : DoubleExchangeRepo {

    override fun provide(currencyIn: String, currencyOut: String): List<DoubleRate> {
        val url = "${SUMO_BASE_URL}/obmen/$currencyIn-$currencyOut-double/"
        val doc = Jsoup.connect(url).get()
        val elements = doc.select("table#exchangesDoubleTable tbody tr")
        val iterator = elements.iterator()
        val rates = ArrayList<DoubleRate>(elements.size / 2)

        while (iterator.hasNext()) {
            val element = iterator.next()

            if (!element.hasClass("item-double"))
                continue

            if (iterator.hasNext()) {
                val details = iterator.next()
                val parsed = parseRate(currencyIn, currencyOut, element, details)

                if (parsed != null)
                    rates.add(parsed)
            }
        }

        return rates
    }

    private fun parseRate(currencyIn: String, currencyOut: String,
                          rate: Element, details: Element): DoubleRate? {
        val amountIn = rate.selectFirst("td.get span.val")
            ?.text()
            ?.replace(" ", "")
            ?.toDoubleOrNull()
            ?: return null
        val amountOut = rate.selectFirst("td.give span.val")
            ?.text()
            ?.replace(" ", "")
            ?.toDoubleOrNull()
            ?: return null
        val amountTransit = rate.selectFirst("td.tranzit span.val")
            ?.text()
            ?.replace(" ", "")
            ?.toDoubleOrNull()
            ?: return null
        val currencyTransit = rate.selectFirst("td.tranzit span.currency")
            ?.text()
            ?: return null
        val course = rate.selectFirst("td.kurs")
            ?.text()
            ?.replace(" ", "")
            ?.toDoubleOrNull()
            ?: return null

        val links = details.select("a.double_changer_link")

        if (links.size < 2) return null

        val firstElem = links[0]
        val secondElem = links[1]

        val firstLink = firstElem.attr("href")
        val secondLink = secondElem.attr("href")
        val firstExchanger = firstElem.text().trim()
        val secondExchanger = secondElem.text().trim()

        return DoubleRate(
            currencyIn,
            currencyOut,
            amountIn,
            amountOut,
            amountTransit,
            currencyTransit,
            course,
            firstLink,
            secondLink,
            firstExchanger,
            secondExchanger
        )
    }

}