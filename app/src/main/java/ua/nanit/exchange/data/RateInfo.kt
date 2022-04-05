package ua.nanit.exchange.data

import ua.nanit.exchange.data.parsed.Organization
import ua.nanit.exchange.data.parsed.RateValue

data class RateInfo(
    val value: RateValue,
    val organization: Organization
)