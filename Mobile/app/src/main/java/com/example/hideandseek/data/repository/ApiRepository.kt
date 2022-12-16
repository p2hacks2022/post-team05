package com.example.hideandseek.data.repository

import com.example.hideandseek.data.api.Params.Companion.BASE_URL
import com.example.hideandseek.data.api.ResponseData
import com.example.hideandseek.data.api.RestApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiRepository {
    private val apiClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val service: RestApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(apiClient)
        .build()
        .create(RestApi::class.java)


    // API通信
    suspend fun updateStatus(id: Int, status: Int): Response<ResponseData.ResponseUpdateStatus> =
        withContext(IO){service.updateStatus(id, status)}

    suspend fun getSpaceTimes(time: String): Response<ResponseData.ResponseGetSpaceTimes> =
        withContext(IO){service.getSpaceTimes(time)}

    companion object Factory {
        val instance: ApiRepository
            @Synchronized get() {
                return ApiRepository()
            }
    }
}