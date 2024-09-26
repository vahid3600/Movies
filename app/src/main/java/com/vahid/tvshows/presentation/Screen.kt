package com.vahid.tvshows.presentation

const val MOVIE_ID = "movieId"

sealed class Screen(val route: String) {
    object MovieSection : Screen(route = "movie_section_screen")
    object MovieDetail : Screen(route = "movie_detail_screen/{$MOVIE_ID}") {
        fun passMovieId(movieId: String): String {
            return "movie_detail_screen/$movieId"
        }
    }
}