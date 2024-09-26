package com.vahid.tvshows.presentation

import kotlinx.coroutines.flow.MutableStateFlow

object SharedData {
    val likedMovie = MutableStateFlow<Int?>(null)
}