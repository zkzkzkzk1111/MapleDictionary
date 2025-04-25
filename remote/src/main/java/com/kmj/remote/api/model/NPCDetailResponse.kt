package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName

data class NPCDetailResponse(
    @SerializedName("id")
    val id :Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("foundAt")
    val foundAt: List<FoundAtItem>?
)

data class FoundAtItem(
    @SerializedName("id")
    val id: Int?
)
