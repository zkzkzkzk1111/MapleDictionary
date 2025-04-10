package com.kmj.data.repository

import com.kmj.data.bound.flowDataResource
import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.data.toDomain
import com.kmj.domain.model.Item
import com.kmj.domain.model.ItemDetail
import com.kmj.domain.model.Monster
import com.kmj.domain.model.MonsterDetail
import com.kmj.domain.repository.MapleStoryRepository
import com.kmj.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MapleStoryRepositoryImpl @Inject constructor(
    private val mapleStoryRemoteDataSource: MapleStoryRemoteDataSource
) : MapleStoryRepository {

    override fun getItems(page: Int, maxEntries: Int): Flow<ApiResult<List<Item>>> =
        flowDataResource { mapleStoryRemoteDataSource.getItem(page, maxEntries).toDomain() }

    override fun getMonsters(page: Int, maxEntries: Int): Flow<ApiResult<List<Monster>>> =
        flowDataResource { mapleStoryRemoteDataSource.getMonster(page, maxEntries).toDomain() }

    override fun getItemDetail(itemId:Int): Flow<ApiResult<ItemDetail>> =
        flowDataResource { mapleStoryRemoteDataSource.getItemDetail(itemId).toDomain() }

    override fun getMonsterDetail(monsterId:Int): Flow<ApiResult<MonsterDetail>> =
        flowDataResource { mapleStoryRemoteDataSource.getMonsterDetail(monsterId).toDomain() }
}

