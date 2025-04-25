package com.kmj.remote.api

import com.kmj.remote.api.model.ItemDetailResponse
import com.kmj.remote.api.model.ItemResponse
import com.kmj.remote.api.model.MapDetailResponse
import com.kmj.remote.api.model.MapResponse
import com.kmj.remote.api.model.MonsterDetailResponse
import com.kmj.remote.api.model.MonsterFoundAtResponse
import com.kmj.remote.api.model.MonsterResponse
import com.kmj.remote.api.model.NPCDetailResponse
import com.kmj.remote.api.model.NPCResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("items/")
    suspend fun getItems(
        @Query("maxEntries") maxEntries: Int,
        @Query("page") page: Int,
        @Query("cash") cash: Boolean=false
    ):ItemResponse

    @GET("item/{itemId}/icon")
    suspend fun getItemImage(@Path("itemId") itemId: Int): ResponseBody

    @GET("monsters/")
    suspend fun getMonsters(
        @Query("maxEntries") maxEntries: Int,
        @Query("page") page: Int,
    ):MonsterResponse
    @GET("monster/{monsterId}/icon")
    suspend fun getMonsterImage(@Path("monsterId") monsterId: Int): ResponseBody

    @GET("item/{itemId}")
    suspend fun getItemDetail(@Path("itemId") itemId: Int): ItemDetailResponse

    @GET("monster/{monsterId}")
    suspend fun getMonsterDetail(@Path("monsterId") monsterId: Int): MonsterDetailResponse


}
interface ApiService1 {
    @GET("KMS/389/mob/{monsterId}")
    suspend fun getMonsterFoundAt(@Path("monsterId") monsterId: Int) : MonsterFoundAtResponse
    @GET("KMS/389/map/{mapId}")
    suspend fun getMapDetail(@Path("mapId") mapId: Int) : MapDetailResponse
    @GET("KMS/389/map")
    suspend fun getMaps() : List<MapResponse>
    @GET("KMS/389/map/{mapId}/icon")
    suspend fun getMapIcons(@Path("mapId") mapId: Int) : ResponseBody
    @GET("KMS/389/NPC")
    suspend fun getNPCs() : List<NPCResponse>
    @GET("KMS/389/NPC/{npcID}")
    suspend fun getNPCDetail(@Path("npcID") npcID:Int) : NPCDetailResponse
}