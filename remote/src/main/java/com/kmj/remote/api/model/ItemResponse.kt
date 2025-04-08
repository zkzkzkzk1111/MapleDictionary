package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.ItemEntity
import com.kmj.remote.RemoteMapper
data class ItemResponse(
    @SerializedName("result")
    val result: List<ItemResult>
)

data class ItemResult(
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("requiredStats")
    val requiredStats :RequiredStatsInfo
) : RemoteMapper<ItemEntity> {
    override fun toData(): ItemEntity =
        ItemEntity(
            itemId = itemId,
            name = name,
            level = requiredStats.level,
            imageUrl = ""

        )
}


data class AvailabilityInfo(
    @SerializedName("cash")
    val cash: Boolean,

    @SerializedName("tradeable")
    val tradeable: Boolean,

    @SerializedName("exclusive")
    val exclusive: Boolean,

    @SerializedName("superior")
    val superior: Boolean,

    @SerializedName("bossDrop")
    val bossDrop: Boolean,

    @SerializedName("durability")
    val durability: Boolean
)

data class RequiredStatsInfo(
    @SerializedName("level")
    val level: Int,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("jobTrees")
    val jobTrees: List<String>,

    @SerializedName("str")
    val str: Int,

    @SerializedName("dex")
    val dex: Int,

    @SerializedName("int")
    val int: Int,

    @SerializedName("luk")
    val luk: Int
)