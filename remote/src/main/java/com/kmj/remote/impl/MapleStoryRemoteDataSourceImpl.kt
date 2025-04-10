package com.kmj.remote.impl

import android.content.Context
import android.util.Log
import com.kmj.data.model.ItemDetailEntity
import com.kmj.data.model.ItemEntity
import com.kmj.data.model.MonsterDetailEntity
import com.kmj.data.model.MonsterEntity
import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.remote.api.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MapleStoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : MapleStoryRemoteDataSource {

    override suspend fun getItem(page: Int, maxEntries: Int): List<ItemEntity> = coroutineScope {
        Log.d("PaginationDebug", "Requesting items with page=$page, maxEntries=$maxEntries")
        val itemsDeferred = async { apiService.getItems(page = page, maxEntries = maxEntries) }
        val items = itemsDeferred.await()
        Log.d("PaginationDebug", "API returned ${items.result.size} items")

        items.result.map { itemResult ->
            val imageDeferred = async { getItemImage(itemResult.itemId) }
            val imageUrl = imageDeferred.await()

            with(itemResult) {
                ItemEntity(
                    itemId = itemId,
                    name = name,
                    level = requiredStats.level,
                    imageUrl = imageUrl
                )
            }
        }
    }

    override suspend fun getMonster(page: Int, maxEntries: Int): List<MonsterEntity> = coroutineScope {
        val monstersDeferred = async { apiService.getMonsters(page = page, maxEntries = maxEntries) }
        val monsters = monstersDeferred.await()
        monsters.result.map { monsterResult ->
            val imageDeferred = async { getMonsterImage(monsterResult.monsterId) }
            val imageUrl = imageDeferred.await()

            with(monsterResult) {
                MonsterEntity(
                    monsterId = monsterId,
                    name = name,
                    level = stats.level,
                    imageUrl = imageUrl
                )
            }
        }
    }



    private suspend fun getItemImage(itemId: Int): String = withContext(Dispatchers.IO) {
        try {
            val responseBody = apiService.getItemImage(itemId)
            val cacheDir = context.cacheDir
            val imageFile = File(cacheDir, "item_${itemId}.png")

            responseBody.byteStream().use { input ->
                FileOutputStream(imageFile).use { output ->
                    input.copyTo(output)
                }
            }

            if (imageFile.exists() && imageFile.length() > 0) {
                Log.d("ImageDebug", "Image saved for item $itemId: ${imageFile.length()} bytes")
                val imageUrl = "file://${imageFile.absolutePath}"
                return@withContext imageUrl
            } else {
                Log.e("ImageDebug", "Failed to save image for item $itemId")
                return@withContext "https://via.placeholder.com/150"
            }
        } catch (e: Exception) {
            Log.e("ImageDebug", "Error processing item $itemId: ${e.message}", e)
            return@withContext "https://via.placeholder.com/150"
        }
    }

    private suspend fun getMonsterImage(monsterId: Int): String = withContext(Dispatchers.IO) {
        try {
            val responseBody = apiService.getMonsterImage(monsterId)
            val cacheDir = context.cacheDir
            val imageFile = File(cacheDir, "monster_${monsterId}.png")

            responseBody.byteStream().use { input ->
                FileOutputStream(imageFile).use { output ->
                    input.copyTo(output)
                }
            }

            if (imageFile.exists() && imageFile.length() > 0) {
                Log.d("ImageDebug", "Image saved for item $monsterId: ${imageFile.length()} bytes")
                val imageUrl = "file://${imageFile.absolutePath}"
                return@withContext imageUrl
            } else {
                Log.e("ImageDebug", "Failed to save image for item $monsterId")
                return@withContext "https://via.placeholder.com/150"
            }
        } catch (e: Exception) {
            Log.e("ImageDebug", "Error processing item $monsterId: ${e.message}", e)
            return@withContext "https://via.placeholder.com/150"
        }
    }

    override suspend fun getItemDetail(itemId: Int): ItemDetailEntity = coroutineScope {
        val itemDetailDeferred = async { apiService.getItemDetail(itemId) }
        val imageDeferred = async { getItemImage(itemId) }

        val itemDetail = itemDetailDeferred.await()
        val imageUrl = imageDeferred.await()

        with(itemDetail) {
            ItemDetailEntity(
                itemId = this.itemId,
                name = name,
                level = requiredStats.level,
                description = description,
                price = availability.shopPrice,
                stats = stats,
                imageUrl = imageUrl,
            )
        }
    }

    override suspend fun getMonsterDetail(monsterId: Int): MonsterDetailEntity = coroutineScope{
        val monsterDetailDeferred = async { apiService.getMonsterDetail(monsterId) }
        val imageDeferred = async { getMonsterImage(monsterId) }

        val monsterDetail = monsterDetailDeferred.await()
        val imageUrl = imageDeferred.await()

        with(monsterDetail) {
            MonsterDetailEntity(
                monsterId = this.monsterId,
                name = name,
                boss = boss,
                imageUrl = imageUrl,
                stats = stats,
                behavior = behavior,
                rewards = rewards,

            )
        }
    }

}