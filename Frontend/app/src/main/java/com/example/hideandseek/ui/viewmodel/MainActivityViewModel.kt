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
//    private val _uiState = MutableStateFlow(MainActivityUiState())
//    val uiState: StateFlow<MainActivityUiState> = _uiState.asStateFlow()

    var gap: Long = 0
    lateinit var relativeTime: String

    fun calculateGap(location: Location): Long {
        gap += 100000000000*sqrt(1-(location.speed/10000).pow(2)).toLong()
        return gap
    }

    fun insert(user: User, context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            UserRepository(context).insert(user)
        }
    }
}