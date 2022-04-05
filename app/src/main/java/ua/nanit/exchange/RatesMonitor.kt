package ua.nanit.exchange

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.nanit.exchange.data.SearchOptions
import ua.nanit.exchange.data.parsed.RatesInfo
import ua.nanit.exchange.log.Logger
import ua.nanit.exchange.storage.RatesStorage
import ua.nanit.exchange.webservice.ApiClient
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class RatesMonitor(private val storage: RatesStorage) : Callback<RatesInfo> {

    private lateinit var updCallback: (RatesInfo) -> Unit
    private lateinit var executor: ScheduledExecutorService
    private lateinit var task: ScheduledFuture<*>
    private lateinit var searchOptions: SearchOptions

    fun start() {
        executor = Executors.newSingleThreadScheduledExecutor()
        try {
            task = executor.scheduleWithFixedDelay(this::update, 0, 10, TimeUnit.SECONDS)
        } catch (th: Throwable) {
            th.printStackTrace()
        }
        searchOptions = SearchOptions(
//            storage.getCurrencyIn(),
//            storage.getCurrencyOut(),
            "qwrub",
            "p24uah",
            storage.getAmountIn(),
            storage.getAmountOut(),
            storage.isCalcCommissions()
        )
    }

    fun stop() {
        task.cancel(true)
        executor.shutdown()
        Logger.info("Monitor stopped")
    }

    fun updateSearchOptions(options: SearchOptions) {
        this.searchOptions = options
        storage.saveOptions(options)
    }

    fun setOnUpdate(callback: (RatesInfo) -> Unit) {
        this.updCallback = callback
    }

    override fun onResponse(call: Call<RatesInfo>, response: Response<RatesInfo>) {
        Logger.info("Response ${response.raw()}")
        val body = response.body()

        if (body != null) {
            Logger.info("Response $body")
            updCallback(body)
            return
        }

        Logger.error("Response body is null")
    }

    override fun onFailure(call: Call<RatesInfo>, t: Throwable) {
        Logger.error("Failed update request: ${t.message}")
    }

    private fun update() {
        val from = searchOptions.currencyIn
        val to = searchOptions.currencyOut

        Logger.info("Request $from->$to")

        if (from != null && to != null) {
            try {
                ApiClient.rateService.getRates(from, to)
                    .enqueue(this)
            } catch (th: Throwable) {
                th.printStackTrace()
            }
        }
    }

}