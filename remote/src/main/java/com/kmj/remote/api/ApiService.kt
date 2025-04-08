package com.kmj.remote.api

import com.kmj.remote.api.model.ItemResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("items/")
    suspend fun getItems():ItemResponse

    @GET("item/{itemId}/icon")
    suspend fun getItemImage(@Path("itemId") itemId: Int): ResponseBody
}