package com.vahid.tvshows.data.remote.model

sealed class RequestResult {
    object Loading : RequestResult()
    class Success<T>(val data: T) : RequestResult()
    class Error(val errorMessage: String) : RequestResult()
}