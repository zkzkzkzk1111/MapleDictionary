package com.kmj.data.remote

import com.kmj.data.model.ItemEntity

interface MapleStoryRemoteDataSource {
    suspend fun getItem():List<ItemEntity>
}