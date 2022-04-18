package ua.nanit.extop.monitoring

import ua.nanit.extop.monitoring.data.Exchanger
import ua.nanit.extop.monitoring.data.Rate

interface ExchangerRepo {

    fun provide(rate: Rate): Exchanger

}