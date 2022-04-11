package ua.nanit.extop.monitoring.exsumo

import org.jsoup.Jsoup
import ua.nanit.extop.monitoring.RatesProvider
import ua.nanit.extop.monitoring.data.Rate

class ExRatesProvider : RatesProvider {

    companion object {
        private const val BASE_URL = "https://exchangesumo.com"
    }

    override fun provide(currencyIn: String, currencyOut: String): List<Rate> {
        val url = "${BASE_URL}/obmen/$currencyIn-$currencyOut"
        val doc = Jsoup.connect(url).get()
        val rows = doc.select("#exchangesTable tbody tr")
        val rates = ArrayList<Rate>(rows.size)

        for (row in rows) {
            val name = row.attr("data-xname")
            val openUrl = row.attr("data-open")
            val link = "${BASE_URL}/$openUrl"
            val amountIn = row.select("td.cell-give var")
                .first()?.html()
            val amountOut = row.select("td.cell-get var")
                .first()?.html()
            val minAmount = row.select("td.cell-give span.currency span.currency-limits sup")
                .first()?.html()
            val fund = row.select("td.cell-rezerv").first()?.html()

            val rate = Rate(
                name,
                amountIn?.toFloat() ?: 0F,
                amountOut?.toFloat() ?: 0F,
                minAmount?.substring(3)?.toInt() ?: 0,
                fund?.replace(" ", "")?.toInt() ?: 0,
                link,
                null
            )

            rates.add(rate)
        }

        return rates
    }

}