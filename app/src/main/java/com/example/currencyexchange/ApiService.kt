package com.example.currencyexchange

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApiService {
    @GET("latest")
    fun getExchangeRates(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String = "USD"
    ): Call<ExchangeRatesResponse>
}