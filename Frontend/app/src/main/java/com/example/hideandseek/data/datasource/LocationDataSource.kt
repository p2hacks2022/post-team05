package com.example.hideandseek.data.datasource

import android.location.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocationDataSource(
    private val locationApi: LocationApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchLatestLocation(): List<Location> =
        withContext(ioDispatcher) {
            locationApi.fetchLatestLocation()
        }
}
interface LocationApi {
    fun fetchLatestLocation(): List<Location>
}