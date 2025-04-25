package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.MapEntity
import com.kmj.remote.RemoteMapper

data class MapResponse (
    @SerializedName("name")
    val name: String,
    @SerializedName("streetName")
    val streetName: String,
    @SerializedName("id")
    val id: Int,
):RemoteMapper<MapEntity> {
    override fun toData(): MapEntity =
        MapEntity (
            id = id,
            name = name,
            streetName = streetName,
            imageUrl = "",
        )
}