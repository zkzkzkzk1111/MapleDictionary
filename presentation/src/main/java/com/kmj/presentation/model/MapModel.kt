package com.kmj.presentation.model

import com.kmj.domain.model.Map

data class MapModel (
    val name :String,
    val streetName :String,
    val id : Int,
    val mapImageUrl : String,
)
fun Map.toPresentation(): MapModel = MapModel(
    name,
    streetName ,
    id,
    mapImageUrl
)