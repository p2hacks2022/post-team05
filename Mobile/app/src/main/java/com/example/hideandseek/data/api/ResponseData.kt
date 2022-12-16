package com.example.hideandseek.data.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponseUpdateStatus(
        @Json(name = "message") val message: String
    )

    data class ResponseGetSpaceTimes(
        @Json(name = "altitude") val altitude: Float,
        @Json(name = "latitude") val latitude: Float,
        @Json(name = "longitude") val longitude: Float,
        @Json(name = "objId") val objId: Int
    )

    data class ResponsePostSpaceTimes(
        @Json(name = "message") val message: String
    )
}