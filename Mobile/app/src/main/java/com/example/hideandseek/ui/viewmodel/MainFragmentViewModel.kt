package com.example.hideandseek.ui.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.datasource.local.UserRoomDatabase
import com.example.hideandseek.data.repository.MapRepository
import com.example.hideandseek.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.pow
import kotlin.math.sqrt

class MainFragmentViewModel: ViewModel() {
    lateinit var allUsersLive: LiveData<List<User>>

    fun setAllUsersLive(context: Context) {
        allUsersLive = UserRepository(context).allUsers.asLiveData()
    }

    private val _limitTime = MutableLiveData<String>()
    val limitTime: LiveData<String> = _limitTime

    fun setLimitTime(relativeTime: String) {
        var limitTime = ""
        if (relativeTime.substring(3, 5).toInt() < 45) {
            limitTime = relativeTime.substring(0, 3) + (relativeTime.substring(3, 5).toInt()+15).toString() + relativeTime.substring(5)
        } else if (relativeTime.substring(3, 5).toInt() < 55) {
            if (relativeTime.substring(0, 2).toInt() == 23) {
                limitTime = "00:0"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            } else if (relativeTime.substring(0, 2).toInt() >= 9) {
                limitTime = (relativeTime.substring(0, 2).toInt()+1).toString()+":0"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            } else {
                limitTime = "0"+(relativeTime.substring(0, 2).toInt()+1).toString()+":0"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            }
        } else {
            if (relativeTime.substring(0, 2).toInt() == 23) {
                limitTime = "00:"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            } else if (relativeTime.substring(0, 2).toInt() >= 9) {
                limitTime = (relativeTime.substring(0, 2).toInt()+1).toString()+":"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            } else {
                limitTime = "0"+(relativeTime.substring(0, 2).toInt()+1).toString()+":"+((relativeTime.substring(3, 5).toInt()+15)%60).toString() + relativeTime.substring(5)
            }
        }
        _limitTime.value = limitTime
    }

    lateinit var location: LiveData<List<User>>

    fun getLocation(relativeTime: String, context: Context) {
        location = UserRepository(context).getLocation(relativeTime).asLiveData()
    }

    private val _map = MutableLiveData<Bitmap>()
    val map: LiveData<Bitmap> = _map

    fun setMap(p0: Bitmap) {
        _map.value = p0
    }



    suspend fun fetchMap(url: String): Bitmap {
        return MapRepository().fetchMap(url)
    }
}