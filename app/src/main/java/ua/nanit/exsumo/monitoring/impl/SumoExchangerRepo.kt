package ua.nanit.exsumo.monitoring.impl

import org.jsoup.Jsoup
import ua.nanit.exsumo.monitoring.ExchangerRepo
import ua.nanit.exsumo.monitoring.data.Exchanger
import ua.nanit.exsumo.monitoring.data.Rate
import ua.nanit.exsumo.monitoring.data.Review

class SumoExchangerRepo : ExchangerRepo {

    companion object {
        private const val IMG_URL = "img.exchangesumo.com"
    }

    override fun provide(rate: Rate): Exchanger {
        val doc = Jsoup.connect(rate.reviewsLink).get()

        val name = doc.select("div.exchanger_title h1").html()
        val url = doc.select("a.about-img")
            .attr("href")
        val iconUrlRaw = doc.select("a.about-img img")
            .attr("src")
        val prefix = if (iconUrlRaw.startsWith("/wp-content")) "https://$IMG_URL" else "https:"
        val iconUrl = "$prefix$iconUrlRaw"

        val reviews = ArrayList<Review>()
        val reviewBlocks = doc.select("blockquote.review-item")
        val dataAbout = doc.select("div.wrap-data dl.data-about").take(5)

        var status = ""
        var fund = ""
        var age = ""
        var country = ""

        for (elem in dataAbout) {
            val key = elem.selectFirst("dt")?.text() ?: ""
            val value = elem.selectFirst("dd")?.text() ?: continue

            if (key == "Статус:") status = value
            if (key == "Сумма резервов:") fund = value
            if (key == "Возраст:") age = value
            if (key == "Страна:") country = value
        }

        for (block in reviewBlocks) {
            val username = block.selectFirst("strong.name-user span")
                ?.text() ?: ""
            val date = block.selectFirst("span.review-date")
                ?.text() ?: ""
            val text = block.selectFirst("div.box-review p")
                ?.text() ?: ""

            reviews.add(Review(
                username,
                date,
                text
            ))
        }

        return Exchanger(
            name,
            url,
            rate.reviewsLink,
            iconUrl,
            status,
            fund,
            age,
            country,
            reviews
        )
    }

}