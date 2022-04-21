package ua.nanit.extop.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val format = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))

fun Double.toRawString(): String {
    format.maximumFractionDigits = 9
    return format.format(this)
}