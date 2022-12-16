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