package com.vahid.tvshows.presentation.movies

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.vahid.tvshows.R
import com.vahid.tvshows.data.remote.model.Movie
import com.vahid.tvshows.data.remote.model.MovieSections
import com.vahid.tvshows.data.remote.model.RequestResult
import com.vahid.tvshows.data.remote.model.Sections
import com.vahid.tvshows.presentation.Screen
import com.vahid.tvshows.presentation.SharedData
import com.vahid.tvshows.presentation.ui.component.SectionHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieSectionsScreen(
    navController: NavController,
    viewModel: MovieSectionsViewModel = hiltViewModel()
) {
    val sectionsResult = viewModel.movieSectionsResult.collectAsStateWithLifecycle()
    Scaffold(content = {
        when (sectionsResult.value) {
            is RequestResult.Success<*> ->
                MovieSectionLazyColumn(
                    sections = ((sectionsResult.value as RequestResult.Success<*>).data as MovieSections).sections,
                    navController = navController
                )

            is RequestResult.Error ->
                Toast.makeText(
                    LocalContext.current,
                    (sectionsResult.value as RequestResult.Error).errorMessage,
                    Toast.LENGTH_SHORT
                ).show()

            RequestResult.Loading -> {}
        }
    })
}

@Composable
fun MovieSectionLazyColumn(sections: List<Sections>, navController: NavController) {
    LazyColumn {
        sections.forEach { section ->
            when (section.cardType) {
                "portrait" ->
                    item {
                        SectionHeader(section.name)
                        MoviesListLazyColumn(section.list, navController = navController)
                    }

                "landscape" ->
                    item {
                        SectionHeader(section.name)
                        CategoryListLazyColumn(section.list)
                    }
            }
        }
    }
}

@Composable
fun MoviesListLazyColumn(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
        reverseLayout = true
    ) {
        items(movies.size + 2) {
            when (it) {
                0, movies.size + 1 -> Spacer(modifier = modifier.width(35.dp))
                else -> MovieCard(movie = movies[it - 1], navController = navController)
            }
        }
    }
}

@Composable
fun CategoryListLazyColumn(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
        reverseLayout = true
    ) {
        items(movies.size + 2) {
            when (it) {
                0, movies.size + 1 -> Spacer(modifier = modifier.width(35.dp))
                else -> CategoryCard(movie = movies[it - 1])
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val likedMovieID = SharedData.likedMovie.collectAsStateWithLifecycle()
    if (likedMovieID.value == movie.id)
        countDownTimer {
            SharedData.likedMovie.value = null
        }
    Column(modifier.clickable { navController.navigate(Screen.MovieDetail.passMovieId(movie.id.toString())) }) {
        Card(
            modifier = modifier
                .width(120.dp)
                .height(160.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(model = movie.imageUrl),
                    contentDescription = movie.nameEn,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                if (likedMovieID.value == movie.id)
                    Image(
                        painter = painterResource(id = R.drawable.like_icon),
                        contentDescription = "",
                        modifier = modifier
                            .align(Alignment.TopEnd)
                            .padding(9.dp)
                            .width(22.dp)
                            .height(22.dp)
                    )
            }
        }
        Text(
            text = movie.nameFa?.ifEmpty { movie.nameEn }.orEmpty(),
            modifier = modifier
                .padding(0.dp, 24.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.End),
            fontSize = 16.sp
        )
    }
}

fun countDownTimer(onComplete: () -> Unit) {
    countDownFlow().onCompletion {
        onComplete()
    }.launchIn(CoroutineScope(Dispatchers.Default))
}

private fun countDownFlow() = flow {
    delay(3_000)
    emit(true)
}


@Composable
fun CategoryCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(0.dp, 0.dp, 0.dp, 50.dp)
            .width(155.dp)
            .height(90.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(model = movie.imageUrl),
                contentDescription = movie.nameEn,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Text(
                text = movie.name.orEmpty(),
                fontSize = 20.sp,
                modifier = modifier
                    .align(alignment = Alignment.CenterEnd)
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
            )
        }
    }
}