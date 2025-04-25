package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.MonsterEntity
import com.kmj.remote.RemoteMapper

data class MonsterResponse (
    @SerializedName("result")
    val result: List<MonsterResult>
)

data class MonsterResult(
    @SerializedName("monsterId")
    val monsterId :Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("stats")
    val stats :StatsInfo
):RemoteMapper<MonsterEntity>{
    override fun toData(): MonsterEntity =
        MonsterEntity(
            monsterId = monsterId,
            name = name,
            level = stats.level,
            imageUrl = "",
            foundAt = emptyList()

        )
}

data class StatsInfo(
    @SerializedName("level")
    val level: Int,

)