package com.kmj.presentation.model

import com.kmj.domain.model.Item

data class ItemModel (
    val id: Int,
    val name : String,
    val level : Int,
    val ItemImageUrl : String,
)

fun Item.toPresentation(): ItemModel = ItemModel(
    id,
    name,
    level,
    ItemImageUrl
)