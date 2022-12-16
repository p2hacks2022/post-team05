package com.example.hideandseek.api

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationService {
    @GET("/ping")
    suspend fun getTest(): Response<ResponseData.ResponsePost>

    @GET("api/v1/spacetimes")
    suspend fun getLocation(@Query("time") time: String):Response<ResponseData.ResponseGetLocation>

    @POST("api/v1/spacetimes")
    suspend fun postLocation(@Body postLocation: PostData.PostLocation): Response<ResponseData.ResponsePost>
}

