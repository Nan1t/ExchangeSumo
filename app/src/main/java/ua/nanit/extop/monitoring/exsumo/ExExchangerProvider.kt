package ua.nanit.extop.monitoring.exsumo

import org.jsoup.Jsoup
import ua.nanit.extop.monitoring.ExchangerProvider
import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Review

class ExExchangerProvider : ExchangerProvider {

    override fun provide(link: String): Exchanger {
        val doc = Jsoup.connect(link).get()
        val name = doc.select("div.exchanger_title h1").html()
        val url = doc.select("a.about-img")
            .attr("href")
        val iconUrlRaw = doc.select("a.about-img img").attr("src")
        val iconUrl = "https:$iconUrlRaw"
        val reviews = ArrayList<Review>()
        val reviewBlocks = doc.select("blockquote.review-item")

        for (block in reviewBlocks) {
            val username = doc.select("strong.name-user span").html()
            val date = doc.select("span.review-date").html()
            val text = doc.select("div.box-review p").html()

            reviews.add(Review(
                username,
                date,
                text
            ))
        }

        return Exchanger(name, url, iconUrl, reviews)
    }

}