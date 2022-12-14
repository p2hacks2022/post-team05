package com.example.hideandseek.data.repository

import android.location.Location
import androidx.annotation.WorkerThread
import com.example.hideandseek.data.datasource.LocationDataSource
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.datasource.local.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository (
    private val userDao: UserDao
    ) {

    val allUsers: Flow<List<User>> = userDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}