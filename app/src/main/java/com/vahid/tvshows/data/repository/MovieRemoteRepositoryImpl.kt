package com.vahid.tvshows.data.repository

import com.vahid.tvshows.data.remote.MovieApi
import com.vahid.tvshows.data.remote.model.MovieDetails
import com.vahid.tvshows.data.remote.model.MovieSections
import javax.inject.Inject

class MovieRemoteRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRemoteRepository {

    override suspend fun getMovieSections(): Result<MovieSections> {
        return runCatching { movieApi.getMovieSections() }
    }

    override suspend fun getMovieDetails(movieId: String): Result<MovieDetails> {
        return runCatching { movieApi.getMovieDetails(movieId) }
    }
}