package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.MonsterDetailEntity
import com.kmj.remote.RemoteMapper

data class MonsterDetailResponse (
    @SerializedName("monsterId")
    val monsterId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("boss")
    val boss:Boolean,
    @SerializedName("stats")
    val stats: Map<String, Any> = emptyMap(),
    @SerializedName("behavior")
    val behavior: Map<String, Any> = emptyMap(),
    @SerializedName("rewards")
    val rewards: Map<String, Any> = emptyMap(),


) : RemoteMapper<MonsterDetailEntity> {
    override fun toData(): MonsterDetailEntity =
        MonsterDetailEntity(
            monsterId = monsterId,
            name = name,
            boss = boss,
            stats = stats,
            behavior = behavior,
            rewards = rewards,
            )
}