package com.kmj.presentation.model

import com.kmj.domain.model.Item
import com.kmj.domain.model.Monster

data class MonsterModel (
    val id : Int,
    val name : String,
    val level: Int,
    val monsterImageUrl : String,
    val foundAt: List<Int>
)
fun Monster.toPresentation(): MonsterModel = MonsterModel(
    id,
    name,
    level,
    monsterImageUrl,
    foundAt
)