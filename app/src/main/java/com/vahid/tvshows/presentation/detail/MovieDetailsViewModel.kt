package com.vahid.tvshows.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahid.tvshows.data.local.model.MovieEntity
import com.vahid.tvshows.data.local.repository.MovieLocalRepository
import com.vahid.tvshows.data.remote.model.RequestResult
import com.vahid.tvshows.data.repository.MovieRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRemoteRepository: MovieRemoteRepository,
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {

    private val _movieDetailsResult = MutableStateFlow<RequestResult>(RequestResult.Loading)
    val movieDetailsResult = _movieDetailsResult

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            movieRemoteRepository.getMovieDetails(movieId).fold(
                onSuccess = {
                    _movieDetailsResult.emit(RequestResult.Success(it))
                },
                onFailure = {
                    _movieDetailsResult.emit(RequestResult.Error(it.message.orEmpty()))
                }
            )
        }
    }

    fun saveMovieInDatabase(movieEntity: MovieEntity){
        viewModelScope.launch {
            movieLocalRepository.saveMovie(movieEntity)
        }
    }
}