package com.example.hideandseek.data.repository

import com.example.hideandseek.api.LocationService
import com.example.hideandseek.api.Params.Companion.BASE_URL
import com.example.hideandseek.api.PostData
import com.example.hideandseek.api.ResponseData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class LocationRepository {
    // 10秒でタイムアウトとなるように設定
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val service: LocationService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(LocationService::class.java)

    suspend fun getLocation(time: String): Response<ResponseData.ResponseGetLocation> =
        withContext(IO) {service.getLocation(time)}

    suspend fun postLocation(postData: PostData.PostLocation): Response<ResponseData.ResponsePost> =
        withContext(IO) {service.postLocation(postData)}

    suspend fun getTest(): Response<ResponseData.ResponsePost> =
        withContext(IO) {service.getTest()}

    companion object Factory {
        val instance: LocationRepository
        @Synchronized get() {
            return LocationRepository()
        }
    }
}