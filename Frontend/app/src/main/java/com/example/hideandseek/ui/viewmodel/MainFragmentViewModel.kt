package com.example.hideandseek.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.datasource.local.UserRoomDatabase
import com.example.hideandseek.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.pow
import kotlin.math.sqrt

data class MainFragmentUiState(
    val allUsers: Flow<List<User>> = emptyFlow(),
    val userMessage: String = ""
)

class MainFragmentViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(MainFragmentUiState())
    val uiState: StateFlow<MainFragmentUiState> = _uiState.asStateFlow()

    lateinit var allUsersLive: LiveData<List<User>>

    fun setAllUsersLive(context: Context) {
        allUsersLive = UserRepository(context).allUsers.asLiveData()
    }

    fun getAll(context: Context) {

    }

    private var fetchJob: Job? = null

    fun fetchRelativeTime(context: Context) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val allUsers = UserRepository(context).allUsers
                _uiState.update {
                    it.copy(allUsers = allUsers)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    val messsages = ioe.message.toString()
                    it.copy(userMessage = messsages)
                }
            }
        }
    }
}