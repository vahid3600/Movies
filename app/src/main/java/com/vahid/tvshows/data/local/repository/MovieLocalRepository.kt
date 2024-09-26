package com.vahid.tvshows.data.local.repository

import com.vahid.tvshows.data.local.model.MovieEntity

interface MovieLocalRepository {

    suspend fun saveMovie(movieEntity: MovieEntity): Result<Unit>
}