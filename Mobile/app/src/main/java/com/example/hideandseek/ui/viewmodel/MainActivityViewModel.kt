package com.example.hideandseek.ui.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.hideandseek.api.LocationService
import com.example.hideandseek.api.PostData
import com.example.hideandseek.api.ResponseData
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.repository.LocationRepository
//import com.example.hideandseek.data.repository.LocationRepository
import com.example.hideandseek.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.LocalTime
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivityViewModel: ViewModel() {
    private val locationRepository = LocationRepository.instance

    private val _postResponse = LiveEvent<Response<ResponseData.ResponsePost>>()
    val postResponse: LiveData<Response<ResponseData.ResponsePost>> get() = _postResponse

    // 現在自国とのずれをnano秒でもつ
    var gap: Long = 0

    // 特殊相対性理論によりずれを計算する
    fun calculateGap(location: Location): Long {
        gap += 1000000000*(1-sqrt(1-(location.speed/100).pow(2))).toLong()
        return gap
    }

    fun getTest() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = locationRepository.getTest()
            _postResponse.postValue(response)
            if (response.isSuccessful) {
                Log.d("GetSuccess", "$response: ${response.body()}")
            } else {
                Log.d("GetFailure", "$response")
            }
        }
    }

    fun postLocation(location: Location, time: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val postData = PostData.PostLocation(time, location.latitude, location.longitude, location.altitude, 1)
                val response = LocationRepository().postLocation(postData)
                Log.d("postData", "$postData")
                if (response.isSuccessful) {
                    Log.d("PostSuccess", "${response}: ${response.body()}")
                } else {
                    Log.d("PostFailure", "$response")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    // Roomデータベースにuserデータを送信
    fun insert(user: User, context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            UserRepository(context).insert(user)
        }
    }
}