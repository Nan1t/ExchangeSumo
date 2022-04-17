package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Exchanger

interface ExchangerProvider {

    fun provide(link: String): Exchanger

}