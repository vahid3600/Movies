package com.vahid.tvshows.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vahid.tvshows.presentation.detail.MovieDetailScreen
import com.vahid.tvshows.presentation.movies.MovieSectionsScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MovieSection.route) {
        composable(
            route = Screen.MovieSection.route) {
            MovieSectionsScreen(navController)
        }
        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument(MOVIE_ID) {
                type = NavType.StringType
            })
        ) {
            MovieDetailScreen(
                movieId = it.arguments?.getString(MOVIE_ID)
            )
        }
    }
}