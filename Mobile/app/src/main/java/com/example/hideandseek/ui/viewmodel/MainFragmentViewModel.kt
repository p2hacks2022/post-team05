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

    // RelativeTime+15分の時間を制限時間とする
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

    private val _isOverLimitTime = MutableLiveData<Boolean>()
    val isOverLimitTime: LiveData<Boolean> = _isOverLimitTime

    // 相対時間が制限時間を超えてたらtrueを返す
    fun compareTime(relativeTime: String, limitTime: String) {
        _isOverLimitTime.value = relativeTime.substring(0, 2) == limitTime.substring(0, 2) && relativeTime.substring(3, 5) == limitTime.substring(3, 5) && relativeTime.substring(6) > limitTime.substring(6)
    }

    private val _isOverSkillTime = MutableLiveData<Boolean>()
    val isOverSkillTime: LiveData<Boolean> = _isOverSkillTime

    fun compareSkillTime(relativeTime: String, skillTime: String) {
        if (relativeTime.substring(6, 7) == skillTime.substring(6, 7)) {
            _isOverSkillTime.value = relativeTime.substring(3, 5).toInt() > skillTime.substring(3, 5).toInt()
        }
    }

    fun howProgressSkillTime(relativeTime: String, skillTime: String): Int {
        if (relativeTime.substring(6).toInt() < skillTime.substring(6).toInt()) {
            return (60+relativeTime.substring(6).toInt()-skillTime.substring(6).toInt())%60
        } else {
            return relativeTime.substring(6).toInt()-skillTime.substring(6).toInt()
        }
    }

    fun setIsOverSkillTime(p0: Boolean) {
        _isOverSkillTime.value = p0
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