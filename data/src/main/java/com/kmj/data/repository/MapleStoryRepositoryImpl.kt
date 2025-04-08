package com.kmj.data.repository

import com.kmj.data.bound.flowDataResource
import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.data.toDomain
import com.kmj.domain.model.Item
import com.kmj.domain.repository.MapleStoryRepository
import com.kmj.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MapleStoryRepositoryImpl @Inject constructor(
    private val mapleStoryRemoteDataSource: MapleStoryRemoteDataSource
) : MapleStoryRepository {

    override fun getItems(): Flow<ApiResult<List<Item>>> =
        flowDataResource { mapleStoryRemoteDataSource.getItem().toDomain() }
}