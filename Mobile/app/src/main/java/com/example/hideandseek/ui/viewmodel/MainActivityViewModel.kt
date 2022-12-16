package com.example.hideandseek.ui.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivityViewModel(): ViewModel() {

    // 現在自国とのずれをnano秒でもつ
    var gap: Long = 0

    // 特殊相対性理論によりずれを計算する
    fun calculateGap(location: Location): Long {
        gap += 1000000000*(1-sqrt(1-(location.speed/100).pow(2))).toLong()
        return gap
    }

    // Roomデータベースにuserデータを送信
    fun insert(user: User, context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            UserRepository(context).insert(user)
        }
    }

    fun deleteAll(context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            UserRepository(context).deleteAll()
        }
    }

    fun resetDatabase(context: Context) {
        deleteAll(context)

        for (hour in 0..0) {
            var relativeTime = "00:00:00"
            // hourが一桁の時は前に0をつける
            relativeTime = if (hour < 10) {
                "0$hour:" + relativeTime.substring(3)
            } else {
                "$hour:" + relativeTime.substring(3)
            }
            for (minute in 5..7) {
                // minuteが一桁の時は前に0をつける
                relativeTime = if (minute < 10) {
                    relativeTime.substring(0, 3) + "0$minute:" + relativeTime.substring(6)
                } else {
                    relativeTime.substring(0, 3) + "$minute:" + relativeTime.substring(6)
                }
                for (second in 0..59) {
                    // secondが一桁の時は前に0をつける
                    relativeTime = if (second < 10) {
                        relativeTime.substring(0, 6) + "0$second"
                    } else {
                        relativeTime.substring(0, 6) + "$second"
                    }
                    var user = User(0, relativeTime, 140.7673718711624, 41.84202707025747)
                    insert(user, context)
                    user = User(0, relativeTime, 140.7673718711624, 41.84222707025747)
                    insert(user, context)
                }
            }
        }
    }
}