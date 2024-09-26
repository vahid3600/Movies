package com.vahid.tvshows.data.local.repository

import com.vahid.tvshows.data.local.AppDatabase
import com.vahid.tvshows.data.local.model.MovieEntity
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : MovieLocalRepository {

    override suspend fun saveMovie(movieEntity: MovieEntity): Result<Unit> {
        return kotlin.runCatching { appDatabase.movieDao().insertMovie(movieEntity) }
    }
}