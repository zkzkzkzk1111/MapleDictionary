package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName

data class MonsterFoundAtResponse (
    @SerializedName("name")
    val name :String,
    @SerializedName("foundAt")
    val foundAt: List<Int>
)