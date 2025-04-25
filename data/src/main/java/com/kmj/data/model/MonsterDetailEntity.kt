package com.kmj.data.model

import com.kmj.data.DataMapper
import com.kmj.domain.model.MonsterDetail

data class MonsterDetailEntity (
    val monsterId : Int,
    val name :String,
    val boss :Boolean,
    val imageUrl : String = "",
    val stats: Map<String, Any>,
    val behavior: Map<String, Any>,
    val rewards: Map<String, Any>,
    val foundAt : List<Int> = emptyList(),
): DataMapper<MonsterDetail> {
    override fun toDomain(): MonsterDetail =
        MonsterDetail(
            id = monsterId,
            name = name,
            boss = boss,
            imageUrl = imageUrl,
            stats = stats,
            behavior = behavior,
            rewards = rewards,
            foundAt = foundAt
        )
}

