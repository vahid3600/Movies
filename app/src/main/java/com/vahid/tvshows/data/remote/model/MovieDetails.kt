package com.vahid.tvshows.data.remote.model

data class MovieDetails(
    var type: String,
    var content: Content,
    var isBuyed: Boolean,
    var buyedTime: Int
)