package com.kmj.data.model

import com.kmj.domain.model.Item
import com.kmj.data.DataMapper

data class ItemEntity (
    val itemId: Int,
    val name :String,
    val level : Int,
    val imageUrl : String = ""
) : DataMapper<Item> {
    override fun toDomain(): Item =
        Item(
            id = itemId,
            name = name,
            level = level,
            ItemImageUrl = imageUrl
        )
}