package ua.nanit.exchange.data.parsed

import ua.nanit.exchange.data.RateInfo

class RatesInfo(
    var values: RateValues,
    var organizations: Map<String, Organization>,
    var currencies: Map<String, Currency>,
) {

    fun buildInfo(): List<RateInfo> {
        val list = ArrayList<RateInfo>(values.values.new.size)

        for (value in values.values.new) {
            val organization = organizations[value.organizationId]

            if (organization != null) {
                list.add(RateInfo(value, organization))
            }
        }

        return list
    }

}