package com.kmj.domain.repository

import com.kmj.domain.model.Item
import com.kmj.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface MapleStoryRepository {
    fun getItems(): Flow<ApiResult<List<Item>>>
}