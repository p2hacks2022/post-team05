package com.example.hideandseek.data.api

import com.squareup.moshi.Json

class PostData {
    data class UpdateStatus(
        @Json(name = "id") val id: Int,
        @Json(name = "status") val status: Int
    )

    data class GetSpaceTimes(
        @Json(name = "time") val time: String
    )

    data class PostSpaceTimes(
        @Json(name = "altitude") val altitude: Float,
        @Json(name = "latitude") val latitude: Float,
        @Json(name = "longitude") val longitude: Float,
        @Json(name = "objId") val objId: Int
    )
}