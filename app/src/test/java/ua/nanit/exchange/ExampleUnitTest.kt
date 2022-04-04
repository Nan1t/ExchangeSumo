package ua.nanit.exchange

import org.junit.Test

import ua.nanit.exchange.network.api.ApiClient
import ua.nanit.exchange.network.CurrencyParser

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val req = ApiClient.rateService.getRates("p24uah", "pmusd", 10)
        val resp = req.execute()

        println("Success: ${resp.isSuccessful}")
        println("Raw: ${resp.raw()}")
        println("ErrorBody: ${resp.errorBody()}")
        println("Body: ${resp.body()}")
    }

    @Test
    fun testLoadingCurrency() {
        CurrencyParser.parse()
    }
}