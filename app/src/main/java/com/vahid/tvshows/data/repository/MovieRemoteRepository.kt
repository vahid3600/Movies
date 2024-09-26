package com.vahid.tvshows.data.repository

import com.vahid.tvshows.data.remote.model.MovieDetails
import com.vahid.tvshows.data.remote.model.MovieSections

interface MovieRemoteRepository {

    suspend fun getMovieSections(): Result<MovieSections>
    suspend fun getMovieDetails(movieId: String): Result<MovieDetails>
}