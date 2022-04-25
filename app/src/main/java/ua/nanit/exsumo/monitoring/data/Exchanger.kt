package ua.nanit.exsumo.monitoring.data

data class Exchanger(
    val name: String,
    val url: String,
    val reviewsUrl: String,
    val iconUrl: String?,
    val status: String,
    val fund: String,
    val age: String,
    val country: String,
    val reviews: List<Review>
) {

    companion object {
        val EMPTY = Exchanger(
            "",
            "",
            "",
            null,
            "",
            "",
            "",
            "",
            emptyList()
        )
    }

    fun isEmpty(): Boolean {
        return this === EMPTY
    }

}