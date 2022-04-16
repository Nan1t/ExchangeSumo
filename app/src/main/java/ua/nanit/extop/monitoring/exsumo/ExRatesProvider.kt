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
        val doc = Jsoup.connect(url)
            .timeout(5000)
            .get()
        val rows = doc.select("#exchangesTable tbody tr")
        val rates = ArrayList<Rate>(rows.size)

        for (row in rows) {
            val name = row.attr("data-xname")
            val openUrl = row.attr("data-open")
            val link = "$BASE_URL$openUrl"
            val amountIn = row.select("td.cell-give var")
                .first()
                ?.html()
                ?.toFloat()
            val amountOut = row.select("td.cell-get var")
                .first()
                ?.html()
                ?.toFloat()
            val minAmount = row.select("td.cell-give span.currency span.currency-limits sup")
                .first()
                ?.html()
                ?.substring(3)
                ?.toFloat()
            val fund = row.select("td.cell-rezerv")
                .first()
                ?.html()
                ?.replace(" ", "")
                ?.toFloat()
            val reviewsRoute = row.select("td.cell-comments")
                .first()
                ?.attr("data-open")
            val reviewsLink = "$BASE_URL$reviewsRoute"

            val rate = Rate(
                name,
                amountIn ?: 0F,
                amountOut ?: 0F,
                minAmount ?: 0.0F,
                fund ?: 0.0F,
                link,
                reviewsLink
            )

            rates.add(rate)
        }

        return rates
    }

}