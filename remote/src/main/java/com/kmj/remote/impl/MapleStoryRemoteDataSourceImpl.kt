package com.kmj.remote.impl

import android.content.Context
import android.util.Log
import com.kmj.data.model.ItemDetailEntity
import com.kmj.data.model.ItemEntity
import com.kmj.data.model.MapDetailEntity
import com.kmj.data.model.MapEntity
import com.kmj.data.model.MonsterDetailEntity
import com.kmj.data.model.MonsterEntity
import com.kmj.data.model.NPCEntity
import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.remote.api.ApiService
import com.kmj.remote.api.ApiService1
import com.kmj.remote.api.model.NPCDetailResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MapleStoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiService1: ApiService1,
    @ApplicationContext private val context: Context
) : MapleStoryRemoteDataSource {

    override suspend fun getItem(page: Int, maxEntries: Int): List<ItemEntity> = coroutineScope {

        val itemsDeferred = async { apiService.getItems(page = page, maxEntries = maxEntries) }
        val items = itemsDeferred.await()


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
            val foundAtDeferred = async { getMonsterFoundAt(monsterResult.monsterId) }

            val imageUrl = imageDeferred.await()
            val foundAt = foundAtDeferred.await()



            with(monsterResult) {
                MonsterEntity(
                    monsterId = monsterId,
                    name = name,
                    level = stats.level,
                    imageUrl = imageUrl,
                    foundAt = foundAt
                )
            }
        }
    }

    override suspend fun getMap(): List<MapEntity> = coroutineScope {
        val mapsDeferred = async { apiService1.getMaps() }
        val maps = mapsDeferred.await()


        maps.map { mapResult ->
            with(mapResult) {
                MapEntity(
                    id = id,
                    name = name,
                    streetName = streetName
                )
            }
        }
    }


    override suspend fun getMapDetail(mapId:Int): MapDetailEntity = coroutineScope {
        val mapDeferred = async { apiService1.getMapDetail(mapId) }
        val mapDetail = mapDeferred.await()

        with(mapDetail) {
            MapDetailEntity(
                id = id,
                name = name,
                streetName = streetName,
                mobIds = mobs.map { it.id }.distinct()
            )
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

    private suspend fun getMapImage(mapId: Int): String = withContext(Dispatchers.IO) {
        try {
            val responseBody = apiService1.getMapIcons(mapId)
            val cacheDir = context.cacheDir
            val imageFile = File(cacheDir, "map_${mapId}.png")

            responseBody.byteStream().use { input ->
                FileOutputStream(imageFile).use { output ->
                    input.copyTo(output)
                }
            }

            if (imageFile.exists() && imageFile.length() > 0) {
                Log.d("ImageDebug", "Image saved for map $mapId:: ${imageFile.length()} bytes")
                val imageUrl = "file://${imageFile.absolutePath}"
                return@withContext imageUrl
            } else {
                Log.e("ImageDebug", "Failed to save image for map $mapId:")
                return@withContext "https://via.placeholder.com/150"
            }
        } catch (e: Exception) {
            Log.e("ImageDebug", "Error processing map $mapId: ${e.message}", e)
            return@withContext "https://via.placeholder.com/150"
        }
    }

    private suspend fun getMonsterFoundAt(monsterId: Int): List<Int> = withContext(Dispatchers.IO) {
        try {
            val response = apiService1.getMonsterFoundAt(monsterId)
            Log.d("FoundAtAPI", "응답: $response")
            return@withContext response.foundAt
        } catch (e: Exception) {
            return@withContext emptyList()
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
        val foundAtDeferred = async { getMonsterFoundAt(monsterId) }
        val monsterDetail = monsterDetailDeferred.await()
        val imageUrl = imageDeferred.await()
        val foundAt = foundAtDeferred.await()

        with(monsterDetail) {
            MonsterDetailEntity(
                monsterId = this.monsterId,
                name = name,
                boss = boss,
                imageUrl = imageUrl,
                stats = stats,
                behavior = behavior,
                rewards = rewards,
                foundAt = foundAt
            )
        }
    }


    override suspend fun getNPC(): List<NPCEntity> = coroutineScope {
        val npcsDeferred = async { apiService1.getNPCs() }
        val npcs = npcsDeferred.await()


        val detailedNpcs = npcs.map { npcResult ->
            async {
                val npcDetail = getMPCDetail(npcResult.id)
                NPCEntity(
                    id = npcResult.id,
                    name = npcDetail.name,
                    foundAt = npcDetail.foundAt
                )
            }
        }
        detailedNpcs.awaitAll()
    }

    private suspend fun getMPCDetail(npcId: Int): NPCDetailResponse = withContext(Dispatchers.IO) {
        try {
            apiService1.getNPCDetail(npcId)
        } catch (e: Exception) {
            Log.e("NPCDebug", "Error fetching NPC detail for $npcId: ${e.message}", e)
            NPCDetailResponse(npcId, "Unknown", emptyList())
        }
    }
}