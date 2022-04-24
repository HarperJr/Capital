package com.harper.capital.network.api

import com.harper.capital.network.ExchangeAuthInterceptor
import com.harper.capital.network.Intercept
import com.harper.capital.network.response.LatestRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Intercept(ExchangeAuthInterceptor::class)
interface ExchangeApi {

    @GET("/latest")
    suspend fun getLatest(@Query("symbols") symbols: String): LatestRatesResponse
}
