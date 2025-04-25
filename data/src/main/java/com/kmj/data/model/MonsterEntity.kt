package com.kmj.data.model

import com.kmj.data.DataMapper

import com.kmj.domain.model.Monster

class MonsterEntity(
    val monsterId: Int,
    val name :String,
    val level : Int,
    val imageUrl : String = "",
    val foundAt: List<Int>,


) : DataMapper<Monster> {
    override fun toDomain(): Monster =
        Monster(
            id = monsterId,
            name = name,
            level = level,
            monsterImageUrl = imageUrl,
            foundAt = foundAt
        )
}