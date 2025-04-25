package com.kmj.domain.model

import kotlin.collections.Map

data class ItemDetail (
    val id : Int,
    val name :String,
    val description :String,
    val price : Int,
    val level : Int,
    val imageUrl : String,
    val stats: Map<String, Int>
)
