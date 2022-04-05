package ua.nanit.exchange.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ua.nanit.exchange.data.parsed.RatesInfo

interface RateService {

    @GET("ajax/rates")
    fun getRates(@Query("currencyFrom") from: String,
                 @Query("currencyTo") to: String,
                 @Query("limit") limit: String = ""): Call<RatesInfo>

}