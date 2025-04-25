package com.kmj.presentation.model

import com.kmj.domain.model.MonsterDetail

data class MonsterDetailModel (
    val id : Int,
    val name :String,
    val boss :Boolean,
    val imageUrl : String,
    val stats: Map<String, Any>,
    val behavior: Map<String, Any>,
    val rewards: Map<String, Any>,
    val foundAt : List<Int>,
)
fun MonsterDetail.toPresentation(): MonsterDetailModel = MonsterDetailModel(
    id,
    name,
    boss,
    imageUrl,
    stats,
    behavior,
    rewards,
    foundAt,
)