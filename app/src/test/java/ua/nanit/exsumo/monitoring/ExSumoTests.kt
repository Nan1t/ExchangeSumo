package ua.nanit.exsumo.monitoring

import org.junit.Before
import org.junit.Test
import ua.nanit.exsumo.TestLogger
import ua.nanit.exsumo.log.Logger
import ua.nanit.exsumo.monitoring.impl.SumoDoubleRatesRepo

class ExSumoTests {

    @Before
    fun initLogger() {
        Logger.init(TestLogger())
    }

    @Test
    fun testDoubleExchangeRepo() {
        val repo = SumoDoubleRatesRepo()
        val rates = repo.provide("QIWIRUR", "MONOBUAH")
        println(rates)
    }

}