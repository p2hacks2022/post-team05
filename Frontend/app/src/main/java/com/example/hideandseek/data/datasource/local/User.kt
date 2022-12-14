package com.example.hideandseek.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_database")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "status") val status: Int,
)