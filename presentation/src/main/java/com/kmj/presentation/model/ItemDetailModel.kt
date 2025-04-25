package com.kmj.presentation.model

import com.kmj.domain.model.ItemDetail

data class ItemDetailModel (
    val id : Int,
    val name :String,
    val description :String,
    val price : Int,
    val level : Int,
    val imageUrl : String,
    val stats: Map<String, Int>
)

fun ItemDetail.toPresentation(): ItemDetailModel = ItemDetailModel(
    id,
    name,
    description,
    price,
    level,
    imageUrl,
    stats,
)