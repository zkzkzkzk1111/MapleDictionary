package com.kmj.remote.impl

import android.content.Context
import android.util.Log
import com.kmj.data.model.ItemEntity
import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.remote.api.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import javax.inject.Inject

class MapleStoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : MapleStoryRemoteDataSource {

    override suspend fun getItem(): List<ItemEntity> = coroutineScope {
        val itemsDeferred = async { apiService.getItems() }
        val items = itemsDeferred.await()

        items.result.take(3).map { itemResult ->
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
}