package com.kmj.data.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.DataMapper
import com.kmj.domain.model.Map

data class MapEntity (
    val name: String?,
    val streetName: String?,
    val id: Int,
    val imageUrl : String = "",
): DataMapper<Map> {
    override fun toDomain(): Map =
        Map(
            name = name.toString(),
            streetName = streetName.toString(),
            id = id,
            mapImageUrl = imageUrl
        )
}