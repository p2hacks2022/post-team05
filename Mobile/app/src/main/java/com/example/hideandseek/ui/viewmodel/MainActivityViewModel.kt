package com.example.hideandseek.ui.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Build
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

        for (hour in 22..22) {
            var relativeTime = ""
            // hourが一桁の時は前に0をつける
            relativeTime += if (hour < 10) {
                "0$hour:"
            } else {
                "$hour:"
            }
            for (minute in 47..49) {
                // jが一桁の時は前に0をつける
                relativeTime += if (minute < 10) {
                    "0$minute:"
                } else {
                    "$minute:"
                }
                for (second in 0..59) {
                    // kが一桁の時は前に0をつける
                    relativeTime += if (second < 10) {
                        "$second"
                    } else {
                        "$second"
                    }
                    val user = User(0, relativeTime, 0.0, 0.0)
                    insert(user, context)
                }
            }
        }
    }
}