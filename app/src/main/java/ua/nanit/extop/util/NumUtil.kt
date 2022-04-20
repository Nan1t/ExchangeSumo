package ua.nanit.extop.util

fun Double.toRawString(): String {
    val formatted = "%.9f".format(this).replace(',', '.')
    var lastZeroIndex = formatted.length
    for (i in formatted.indices.reversed()) {
        if (formatted[i] != '0' && formatted[i] != '.') break
        lastZeroIndex--
    }
    return formatted.substring(0, lastZeroIndex)
}