package ua.nanit.extop.monitoring.data

data class Exchanger(
    val id: String,
    val name: String?,
    val verified: Boolean,
    val meta: Map<String, String>
)