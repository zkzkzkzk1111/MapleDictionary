package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.ItemDetailEntity
import com.kmj.remote.RemoteMapper

data class ItemDetailResponse(
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description:String,
    @SerializedName("requiredStats")
    val requiredStats :requiredStats,
    @SerializedName("availability")
    val availability : availability,
    @SerializedName("stats")
    val stats: Map<String, Int> = emptyMap()

) : RemoteMapper<ItemDetailEntity> {
    override fun toData(): ItemDetailEntity =
        ItemDetailEntity(
            itemId = itemId,
            name = name,
            level = requiredStats.level,
            price = availability.shopPrice,
            description = description,
            stats = stats,


        )
}


data class availability(
    @SerializedName("cash")
    val cash: Boolean,

    @SerializedName("shopPrice")
    val shopPrice: Int,
)

data class requiredStats(
    @SerializedName("level")
    val level: Int,

    @SerializedName("gender")
    val gender: String,
)