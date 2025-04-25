package com.kmj.domain.model

import kotlin.collections.Map

data class MonsterDetail (
    val id : Int,
    val name :String,
    val boss :Boolean,
    val imageUrl : String,
    val stats: Map<String, Any>,
    val behavior: Map<String, Any>,
    val rewards: Map<String, Any>,
    val foundAt : List<Int>,
)