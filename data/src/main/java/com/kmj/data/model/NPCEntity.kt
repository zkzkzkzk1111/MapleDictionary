package com.kmj.data.model

import com.kmj.data.DataMapper
import com.kmj.domain.model.NPC

data class NPCEntity (
    val id: Int,
    val name: String? = "",
    val foundAt: List<Int>? = emptyList(),
): DataMapper<NPC> {
    override fun toDomain(): NPC = NPC(
        id = id,
        name = name,
        foundAt = foundAt
    )
}
