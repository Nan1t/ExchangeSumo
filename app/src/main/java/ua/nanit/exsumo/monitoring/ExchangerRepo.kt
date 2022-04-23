package ua.nanit.exsumo.monitoring

import ua.nanit.exsumo.monitoring.data.Exchanger
import ua.nanit.exsumo.monitoring.data.Rate

interface ExchangerRepo {

    fun provide(rate: Rate): Exchanger

}