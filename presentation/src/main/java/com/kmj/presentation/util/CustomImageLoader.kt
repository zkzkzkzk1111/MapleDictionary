package com.kmj.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomImageLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

    @Composable
    fun loadImage(url: String): AsyncImagePainter {
        return rememberAsyncImagePainter(
            model = url,
            imageLoader = imageLoader
        )
    }
}