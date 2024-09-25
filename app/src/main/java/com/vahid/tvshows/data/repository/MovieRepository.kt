package com.vahid.tvshows.data.repository

import com.vahid.tvshows.data.remote.model.MovieSections

interface MovieRepository {

    suspend fun getMovieSections(): Result<MovieSections>
}