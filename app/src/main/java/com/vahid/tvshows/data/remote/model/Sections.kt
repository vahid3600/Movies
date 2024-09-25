package com.vahid.tvshows.data.remote.model

data class Sections(
    var id: Int,
    var name: String,
    var cardType: String,
    var listType: String,
    var list: ArrayList<Movie>
)