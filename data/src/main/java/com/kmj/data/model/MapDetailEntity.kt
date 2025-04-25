package com.kmj.data.model

import com.kmj.data.DataMapper
import com.kmj.domain.model.MapDetail

data class MapDetailEntity (
    val id: Int,
    val name: String,
    val streetName:String,
    val mobIds: List<Int>
) : DataMapper<MapDetail> {
    override fun toDomain(): MapDetail =
        MapDetail(
            id=id,
            name=name,
            streetName = streetName,
            mobs = mobIds
        )
}