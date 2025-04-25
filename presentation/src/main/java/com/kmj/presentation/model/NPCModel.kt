package com.kmj.presentation.model

import com.kmj.domain.model.NPC

data class NPCModel(
    val id: Int,
    val name: String?,
    val foundAt: List<Int>?
)


fun NPC.toPresentation(): NPCModel = NPCModel(
    id = id,
    name = name,
    foundAt = foundAt
)