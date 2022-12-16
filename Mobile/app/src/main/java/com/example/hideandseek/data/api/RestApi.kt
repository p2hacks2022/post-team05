package com.example.hideandseek.data.api

import com.example.hideandseek.data.api.PostData.*
import com.example.hideandseek.data.api.ResponseData.*
import retrofit2.Response
import retrofit2.http.*

interface RestApi {
    @POST("/api/v1/players/:id/status/:status")
    suspend fun updateStatus(@Path("id") id: Int, @Path("status") status: Int): Response<ResponseUpdateStatus>

    @GET("/api/v1/spacetimes")
    suspend fun getSpaceTimes(@Query("time") time: String): Response<ResponseGetSpaceTimes>

    // @POST("/api/v1/spacetimes")
    // suspend fun postSpaceTimes(@Body )
}