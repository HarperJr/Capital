package com.harper.capital.network

import com.google.gson.GsonBuilder
import com.harper.capital.network.api.ExchangeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val networkModule
    get() = module {

        single {
            GsonBuilder()
                .serializeNulls()
                .create()
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(InterceptorsHolder())
                .addInterceptor(
                    HttpLoggingInterceptor { log -> Timber.d(log) }
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .callTimeout(5L, TimeUnit.SECONDS)
                .build()
        }

        factory { (baseUrl: String) ->
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }

        single {
            get<Retrofit> { parametersOf(BuildConfig.EXCHANGE_URL) }
                .create(ExchangeApi::class.java)
        }
    }
