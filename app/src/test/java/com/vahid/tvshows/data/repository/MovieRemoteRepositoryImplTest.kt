package com.vahid.tvshows.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vahid.tvshows.data.remote.MovieApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRemoteRepositoryImplTest {

    private val movieApi: MovieApi = mock()
    private val movieRepositoryImpl = MovieRemoteRepositoryImpl(movieApi)

    @Test
    fun `getMovieSections calls movieApi getMovieSections`() = runTest {
        movieRepositoryImpl.getMovieSections()

        verify(movieApi).getMovieSections()
    }

    @Test
    fun `getMovieDetails calls movieApi getMovieDetails`() = runTest {
        movieRepositoryImpl.getMovieDetails("12345")

        verify(movieApi).getMovieDetails("12345")
    }
}