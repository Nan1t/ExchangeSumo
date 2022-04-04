package ua.nanit.exchange.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.nanit.exchange.data.RatesInfo
import ua.nanit.exchange.log.Logger
import ua.nanit.exchange.network.api.ApiClient
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class RatesMonitor : Callback<RatesInfo> {

    private lateinit var updCallback: (RatesInfo) -> Unit
    private lateinit var executor: ScheduledExecutorService
    private lateinit var task: ScheduledFuture<*>

    private var currencyFrom: String? = null
    private var currencyTo: String? = null

    fun start() {
        executor = Executors.newSingleThreadScheduledExecutor()
        task = executor.scheduleWithFixedDelay(this::update, 0, 1, TimeUnit.MINUTES)
    }

    fun stop() {
        task.cancel(true)
        executor.shutdown()
    }

    fun updateCurrency(from: String?, to: String?) {
        currencyFrom = from
        currencyTo = to
    }

    fun setOnUpdate(callback: (RatesInfo) -> Unit) {
        this.updCallback = callback
    }

    override fun onResponse(call: Call<RatesInfo>, response: Response<RatesInfo>) {
        val body = response.body()

        if (body != null) {
            updCallback(body)
            return
        }

        Logger.error("Response body is null")
    }

    override fun onFailure(call: Call<RatesInfo>, t: Throwable) {
        Logger.error("Failed update request: ${t.message}")
    }

    private fun update() {
        val from = currencyFrom
        val to = currencyTo

        if (from != null && to != null) {
            ApiClient.rateService.getRates(from, to)
                .enqueue(this)
        }
    }

}