package ua.nanit.exchange.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ua.nanit.exchange.data.RatesInfo

interface RateService {

    @GET("/ajax/rates")
    fun getRates(@Query("currencyFrom") from: String,
                 @Query("currencyTo") to: String,
                 @Query("limit") limit: Int = 10): Call<RatesInfo>

}