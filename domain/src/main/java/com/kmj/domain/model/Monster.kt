package com.kmj.domain.model

data class Monster (
    val id : Int,
    val name : String,
    val level: Int,
    val monsterImageUrl : String,
    val foundAt: List<Int>
)