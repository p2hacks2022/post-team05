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
import kotlin.math.roundToInt
import kotlin.math.sqrt

class MainActivityViewModel(): ViewModel() {

    // 特殊相対性理論によりずれを計算する
    fun calculateGap(location: Location): Long {
        Log.d("GAP", "speed: ${location.speed}, calc: ${(1000000000 * (1 - sqrt(1 - (location.speed / 10).pow(2)))).roundToInt().toLong()}")
        return  (1000000000 * (1 - sqrt(1 - (location.speed / 10).pow(2)))).roundToInt().toLong()
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
}
