package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName

data class ItemDetailResponse(
    @SerializedName("category") val category: String,
    @SerializedName("availability") val availability: AvailabilityInfo,
    @SerializedName("imageUrl") val imageUrl: String? = null
)