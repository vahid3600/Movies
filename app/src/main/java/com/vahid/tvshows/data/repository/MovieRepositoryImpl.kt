package com.vahid.tvshows.data.repository

import com.vahid.tvshows.data.remote.MovieApi
import com.vahid.tvshows.data.remote.model.MovieSections
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getMovieSections(): Result<MovieSections> {
        return runCatching { movieApi.getMovieSections() }
    }
}