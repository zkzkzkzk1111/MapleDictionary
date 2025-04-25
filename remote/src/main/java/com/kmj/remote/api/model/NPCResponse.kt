package com.kmj.remote.api.model

import com.google.gson.annotations.SerializedName
import com.kmj.data.model.NPCEntity
import com.kmj.remote.RemoteMapper

data class NPCResponse (
    @SerializedName("id")
    val id :Int,
):RemoteMapper<NPCEntity> {
    override fun toData(): NPCEntity =
        NPCEntity(
            id
        )
}