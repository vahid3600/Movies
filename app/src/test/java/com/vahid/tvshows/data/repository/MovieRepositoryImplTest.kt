package com.vahid.tvshows.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vahid.tvshows.data.remote.MovieApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    private val movieApi: MovieApi = mock()
    private val movieRepositoryImpl = MovieRepositoryImpl(movieApi)

    @Test
    fun `getMovieSections calls movieApi getMovieSections`() = runTest {
        movieRepositoryImpl.getMovieSections()

        verify(movieApi).getMovieSections()
    }
}