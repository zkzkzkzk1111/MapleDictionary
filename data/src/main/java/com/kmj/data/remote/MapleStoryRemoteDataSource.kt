package com.kmj.data.remote

import com.kmj.data.model.ItemDetailEntity
import com.kmj.data.model.ItemEntity
import com.kmj.data.model.MapDetailEntity
import com.kmj.data.model.MapEntity
import com.kmj.data.model.MonsterDetailEntity
import com.kmj.data.model.MonsterEntity
import com.kmj.data.model.NPCEntity

interface MapleStoryRemoteDataSource {
    suspend fun getItem(page: Int, maxEntries: Int):List<ItemEntity>
    suspend fun getMonster(page: Int, maxEntries: Int): List<MonsterEntity>
    suspend fun getMap(): List<MapEntity>
    suspend fun getNPC(): List<NPCEntity>
    suspend fun getItemDetail(itemId:Int):ItemDetailEntity
    suspend fun getMonsterDetail(monsterId:Int): MonsterDetailEntity
    suspend fun getMapDetail(mapId:Int): MapDetailEntity
}