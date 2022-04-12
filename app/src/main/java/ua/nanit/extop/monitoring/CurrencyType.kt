package ua.nanit.extop.monitoring

enum class CurrencyType(val typeId: Int) {

    IN(0),
    OUT(1);

    companion object {
        fun byId(id: Int): CurrencyType? {
            return when(id) {
                IN.typeId -> IN
                OUT.typeId -> OUT
                else -> null
            }
        }
    }
}