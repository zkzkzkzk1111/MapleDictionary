package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.MapDetailEntity
import com.kmj.remote.RemoteMapper

data class MapDetailResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("streetName")
    val streetName:String,
    @SerializedName("mobs")
    val mobs: List<MapMob>
): RemoteMapper<MapDetailEntity>{
    override fun toData(): MapDetailEntity =
        MapDetailEntity(
            id = id,
            name = name,
            streetName = streetName,
            mobIds = mobs.map { it.id }.distinct()
        )
}

data class MapMob(
    @SerializedName("id")
    val id: Int
)