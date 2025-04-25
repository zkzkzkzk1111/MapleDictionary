package com.kmj.presentation.model

import com.kmj.domain.model.MapDetail

data class MapDetailModel (
    val id : Int,
    val name :String,
    val streetName : String,
    val mobs : List<Int>

)

fun MapDetail.toPresentation(): MapDetailModel = MapDetailModel(
    id,
    name,
    streetName,
    mobs,
)