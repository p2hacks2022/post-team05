package com.example.hideandseek.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponsePost(
        @Json(name = "message") val message: String
    )

    data class ResponseGetLocation(
        @Json(name = "altitude") val altitude: Double,
        @Json(name = "latitude") val latitude: Double,
        @Json(name = "longitude") val longitude: Double,
        @Json(name = "obj_id") val objId: Int
    )
}