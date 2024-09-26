package com.vahid.tvshows.di

import com.vahid.tvshows.data.local.repository.MovieLocalRepository
import com.vahid.tvshows.data.local.repository.MovieLocalRepositoryImpl
import com.vahid.tvshows.data.repository.MovieRemoteRepository
import com.vahid.tvshows.data.repository.MovieRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMovieRemoteRepository(
        movieRemoteRepository: MovieRemoteRepositoryImpl
    ): MovieRemoteRepository

    @Binds
    fun bindMovieLocalRepository(
        movieLocalRepository: MovieLocalRepositoryImpl
    ): MovieLocalRepository
}