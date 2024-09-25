package com.vahid.tvshows.data.remote

import com.vahid.tvshows.data.remote.model.MovieSections
import retrofit2.http.GET

interface MovieApi {

    @GET("config/pages/tabs/5850835/sections")
    suspend fun getMovieSections(): MovieSections
}