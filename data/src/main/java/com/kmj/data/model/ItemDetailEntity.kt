package com.kmj.data.model

import com.kmj.data.DataMapper
import com.kmj.domain.model.ItemDetail

data class ItemDetailEntity (
    val itemId : Int,
    val name :String,
    val description :String?,
    val price : Int,
    val level : Int,
    val imageUrl : String = "",
    val stats: Map<String, Int>
): DataMapper<ItemDetail> {
    override fun toDomain(): ItemDetail =
        ItemDetail(
            id = itemId,
            name = name,
            description =description?:"",
            price = price,
            level = level,
            imageUrl = imageUrl,
            stats=  stats
        )
}