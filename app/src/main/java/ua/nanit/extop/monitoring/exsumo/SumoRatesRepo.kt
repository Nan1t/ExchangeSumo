package ua.nanit.extop.monitoring.exsumo

import org.jsoup.Jsoup
import ua.nanit.extop.monitoring.RatesRepo
import ua.nanit.extop.monitoring.data.Rate

class SumoRatesRepo : RatesRepo {

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
            val amountIn = row.selectFirst("td.cell-give var")
                ?.html()
                ?.toFloatOrNull()
            val amountOut = row.selectFirst("td.cell-get var")
                ?.html()
                ?.toFloatOrNull()
            val minAmount = row.selectFirst("td.cell-give span.currency span.currency-limits sup")
                ?.html()
                ?.substring(3)
                ?.toFloatOrNull()
                ?.toInt()
            val fund = row.selectFirst("td.cell-rezerv")
                ?.html()
                ?.replace(" ", "")
                ?.toFloatOrNull()
                ?.toInt()
            val reviewsRoute = row.selectFirst("td.cell-comments")
                ?.attr("data-open")
            val reviewsLink = "$BASE_URL$reviewsRoute"
            val isManual = row.selectFirst("div.wrap-badge span.data-badge_param_manual") != null
            val isMediator = row.selectFirst("div.wrap-badge span.data-badge_param_is_mediator") != null

            val rate = Rate(
                name,
                amountIn ?: 0F,
                amountOut ?: 0F,
                minAmount ?: 0,
                fund ?: 0,
                link,
                reviewsLink,
                isManual,
                isMediator
            )

            rates.add(rate)
        }

        return rates
    }

}