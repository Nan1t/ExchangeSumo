package ua.nanit.exsumo.monitoring.impl

import org.jsoup.Jsoup
import ua.nanit.exsumo.monitoring.RatesRepo
import ua.nanit.exsumo.monitoring.data.Rate

class SumoRatesRepo : RatesRepo {

    override fun provide(currencyIn: String, currencyOut: String): List<Rate> {
        val url = "${SUMO_BASE_URL}/obmen/$currencyIn-$currencyOut"
        val doc = Jsoup.connect(url).get()
        val rows = doc.select("#exchangesTable tbody tr")
        val rates = ArrayList<Rate>(rows.size)

        for (row in rows) {
            val name = row.attr("data-xname")
            val openUrl = row.attr("data-open")
            val link = "$SUMO_BASE_URL$openUrl"
            val amountIn = row.selectFirst("td.cell-give var")
                ?.text()
                ?.replace(" ", "")
                ?.toDoubleOrNull()
            val amountOut = row.selectFirst("td.cell-get var")
                ?.text()
                ?.replace(" ", "")
                ?.toDoubleOrNull()
            val minAmount = row.selectFirst("td.cell-give span.currency span.currency-limits sup")
                ?.text()
                ?.substring(3)
                ?.toDoubleOrNull()
            val fund = row.selectFirst("td.cell-rezerv .txt-reserv + span")
                ?.text()
                ?.replace(" ", "")
                ?.toDoubleOrNull()
            val reviewsRoute = row.selectFirst("td.cell-comments")
                ?.attr("data-open")
            val reviewsLink = "$SUMO_BASE_URL$reviewsRoute"
            val isManual = row.selectFirst("div.wrap-badge span.data-badge_param_manual") != null
            val isMediator = row.selectFirst("div.wrap-badge span.data-badge_param_is_mediator") != null
            val isCardVerify = row.selectFirst("div.wrap-badge span.data-badge_param_cardverify") != null

            val rate = Rate(
                name,
                amountIn ?: 0.0,
                amountOut ?: 0.0,
                currencyIn,
                currencyOut,
                minAmount ?: 0.0,
                fund ?: 0.0,
                link,
                reviewsLink,
                isManual,
                isMediator,
                isCardVerify
            )

            rates.add(rate)
        }

        return rates
    }

}