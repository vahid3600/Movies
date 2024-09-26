package com.vahid.tvshows.data.remote

import com.vahid.tvshows.data.remote.model.MovieDetails
import com.vahid.tvshows.data.remote.model.MovieSections
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("config/pages/tabs/5850835/sections")
    suspend fun getMovieSections(): MovieSections

    @GET("contents/{MOVIE_ID}")
    suspend fun getMovieDetails(
        @Path("MOVIE_ID") movieId: String
    ): MovieDetails
}