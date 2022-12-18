package com.example.hideandseek.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.net.URL

class MapRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun fetchMap(url: String): Bitmap {
        val originalDeferred = coroutineScope.async(Dispatchers.IO) {
            getOriginalBitmap(url)
        }
        return originalDeferred.await()
    }

    private fun getOriginalBitmap(url: String): Bitmap =
        URL(url).openStream().use {
            BitmapFactory.decodeStream(it)
        }
}