package ua.nanit.extop.monitoring.data

data class Exchanger(
    val name: String,
    val url: String,
    val iconUrl: String?,
    val reviews: List<Review>
)