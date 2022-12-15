package com.example.hideandseek.data.datasource.local

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase: RoomDatabase(), ViewModelProvider.Factory {

    abstract fun userDao() : UserDao

    companion object {
        private var INSTANCE: UserRoomDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): UserRoomDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}