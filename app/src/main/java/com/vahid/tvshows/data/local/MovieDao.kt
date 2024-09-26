package com.vahid.tvshows.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.vahid.tvshows.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity): Long
}