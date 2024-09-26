package com.vahid.tvshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vahid.tvshows.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}