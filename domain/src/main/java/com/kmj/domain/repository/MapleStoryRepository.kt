package com.kmj.domain.repository

import com.kmj.domain.model.Item
import com.kmj.domain.model.ItemDetail
import com.kmj.domain.model.Map
import com.kmj.domain.model.MapDetail
import com.kmj.domain.model.Monster
import com.kmj.domain.model.MonsterDetail
import com.kmj.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface MapleStoryRepository {

    fun getItems(page: Int, maxEntries: Int): Flow<ApiResult<List<Item>>>
    fun getItemDetail(itemId:Int): Flow<ApiResult<ItemDetail>>
    fun getMaps(): Flow<ApiResult<List<Map>>>
    fun getMonsters(page: Int, maxEntries: Int): Flow<ApiResult<List<Monster>>>
    fun getMonsterDetail(monsterId:Int): Flow<ApiResult<MonsterDetail>>
    fun getMapDetail(mapId:Int): Flow<ApiResult<MapDetail>>
}