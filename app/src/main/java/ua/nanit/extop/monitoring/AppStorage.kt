package ua.nanit.extop.monitoring

interface AppStorage {

    fun getMonitoringType(): Int

    fun changeMonitoringType(type: Int)

}