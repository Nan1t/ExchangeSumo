package ua.nanit.extop.monitoring

import org.junit.Before
import org.junit.Test
import ua.nanit.extop.TestLogger
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.exsumo.ExCurrencyParser

class ExSumoTests {

    @Before
    fun initLogger() {
        Logger.init(TestLogger())
    }

    @Test
    fun testCurrenciesLoading() {

    }

}