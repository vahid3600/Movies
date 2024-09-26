package com.vahid.tvshows.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahid.tvshows.data.remote.model.RequestResult
import com.vahid.tvshows.data.repository.MovieRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSectionsViewModel @Inject constructor(
    private val movieRemoteRepository: MovieRemoteRepository
) : ViewModel() {

    init {
        getMovieSections()
    }

    private val _movieSectionsResult = MutableStateFlow<RequestResult>(RequestResult.Loading)
    val movieSectionsResult = _movieSectionsResult

    private fun getMovieSections() {
        viewModelScope.launch {
            movieRemoteRepository.getMovieSections(
            ).fold(
                onSuccess = {
                    _movieSectionsResult.emit(RequestResult.Success(it))
                },
                onFailure = {
                    _movieSectionsResult.emit(RequestResult.Error(it.message.orEmpty()))
                }
            )
        }
    }
}