package com.harper.capital.network

import okhttp3.Interceptor
import okhttp3.Response

private const val ACCESS_KEY = "access_key"

internal class ExchangeAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(ACCESS_KEY, BuildConfig.EXCHANGE_API_KEY).build()
        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }
}
