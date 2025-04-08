package com.kmj.remote.api

import com.kmj.remote.api.interceptor.RequestHeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30

fun createApiService(baseUrl: String): ApiService {
    val okHttpClient = OkHttpClient.Builder().apply {
        readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

        addNetworkInterceptor(RequestHeaderInterceptor())

    }.build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}