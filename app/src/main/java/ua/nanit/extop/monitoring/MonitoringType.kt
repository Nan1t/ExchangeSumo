package ua.nanit.extop.monitoring

import java.lang.IllegalArgumentException

enum class MonitoringType(val typeId: Int) {

    EXCHANGE_SUMO(0),
    BEST_CHANGE(1);

    companion object {
        fun byId(id: Int): MonitoringType {
            return when(id) {
                EXCHANGE_SUMO.typeId -> EXCHANGE_SUMO
                BEST_CHANGE.typeId -> BEST_CHANGE
                else -> throw IllegalArgumentException("Invalid monitoring type id")
            }
        }
    }

}