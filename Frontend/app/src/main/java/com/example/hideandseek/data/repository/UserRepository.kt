package com.example.hideandseek.data.repository

import android.content.Context
import android.location.Location
import androidx.annotation.WorkerThread
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.datasource.local.UserDao
import com.example.hideandseek.data.datasource.local.UserRoomDatabase
import kotlinx.coroutines.flow.Flow

class UserRepository (private val context: Context) {

    private val userDao: UserDao by lazy { UserRoomDatabase.getInstance(context).userDao() }

    val allUsers: Flow<List<User>> = userDao.getAll()

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}