package com.vahid.tvshows.presentation.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.vahid.tvshows.R
import com.vahid.tvshows.data.local.model.MovieEntity
import com.vahid.tvshows.data.remote.model.MovieDetails
import com.vahid.tvshows.data.remote.model.RequestResult
import com.vahid.tvshows.presentation.SharedData
import com.vahid.tvshows.presentation.ui.component.SectionHeader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieId: String?,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val detailsResult = viewModel.movieDetailsResult.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        movieId?.let {
            viewModel.getMovieDetails(it)
        }
    }

    LaunchedEffect(key1 = detailsResult.value) {
        val movieDetails = (detailsResult.value as? RequestResult.Success<*>)?.data as? MovieDetails
        movieDetails?.let {
            viewModel.saveMovieInDatabase(
                MovieEntity(
                    movieDetails.content.id,
                    movieDetails.content.nameEn
                )
            )
        }
    }

    Scaffold(content = {
        when (detailsResult.value) {
            is RequestResult.Success<*> -> {
                MovieDetailsScrollableColumn(((detailsResult.value as RequestResult.Success<*>).data as MovieDetails))
            }

            is RequestResult.Error ->
                Toast.makeText(
                    LocalContext.current,
                    (detailsResult.value as RequestResult.Error).errorMessage,
                    Toast.LENGTH_SHORT
                ).show()

            RequestResult.Loading -> {}
        }
    })
}

@Composable
fun MovieDetailsScrollableColumn(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = movieDetails.content.imageUrl),
            contentDescription = movieDetails.content.mobileBannerUrl,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black
                        ),
                        tileMode = TileMode.Clamp
                    )
                )
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = movieDetails.content.nameFa,
                fontSize = 28.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(50.dp, 118.dp, 50.dp, 0.dp),
                textAlign = TextAlign.End
            )
            MovieDetailsList(movieDetails)
            Text(
                text = movieDetails.content.description,
                fontSize = 14.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(50.dp, 21.dp, 50.dp, 0.dp),
                textAlign = TextAlign.End
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(50.dp, 47.dp, 50.dp, 0.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LikeButton(movieDetails)
                Spacer(modifier = modifier.width(13.dp))
                PlayButton()
            }
            MoviePicturesList(movieDetails)
        }
    }
}

@Composable
fun MovieDetailsList(movieDetails: MovieDetails, modifier: Modifier = Modifier) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 19.dp, 50.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ColoredDetailView(
                name = stringResource(
                    id = R.string.imdb,
                    movieDetails.content.imdbRate),
                color = colorResource(id = R.color.imdb_color)
            )
            Spacer(modifier = modifier.width(12.dp))
            ColoredDetailView(
                name = movieDetails.content.duration.toString(),
                color = colorResource(id = R.color.background_detail_color)
            )
            Spacer(modifier = modifier.width(12.dp))
            ColoredDetailView(
                name = movieDetails.content.ages.toString(),
                color = colorResource(id = R.color.age_color)
            )
        }
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 19.dp, 50.dp, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End)
        ) {
            items(movieDetails.content.genres.size) {
                GenreView(movieDetails.content.genres[it].name)
            }
        }
    }
}

@Composable
fun ColoredDetailView(name: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            modifier = modifier.padding(12.dp, 0.dp, 12.dp, 0.dp)
        )
    }
}

@Composable
fun GenreView(name: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.background_detail_color))
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            modifier = modifier.padding(12.dp, 0.dp, 12.dp, 0.dp)
        )
    }
}

@Composable
fun PlayButton(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(colorResource(id = R.color.play_background_color)),
        modifier = modifier.height(40.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.play),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(15.dp, 0.dp, 4.dp, 0.dp)
                    .align(Alignment.CenterVertically),
                color = Color.Black
            )
            Image(
                modifier = modifier
                    .padding(0.dp, 9.dp, 15.dp, 9.dp)
                    .width(20.dp)
                    .height(20.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.play_icon),
                contentDescription = stringResource(R.string.play)
            )
        }
    }
}

@Composable
fun LikeButton(movieDetails: MovieDetails, modifier: Modifier = Modifier) {
    var likeStatus by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(colorResource(id = R.color.like_background_color)),
        modifier = modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Image(
            modifier = modifier
                .padding(7.dp)
                .clickable {
                    likeStatus = !likeStatus
                    if (likeStatus)
                        SharedData.likedMovie.value = movieDetails.content.id
                },
            painter = painterResource(id = if (likeStatus) R.drawable.like_active else R.drawable.like),
            contentDescription = stringResource(R.string.play),
        )
    }
}

@Composable
fun MoviePicturesList(movieDetails: MovieDetails, modifier: Modifier = Modifier) {
    SectionHeader(stringResource(id = R.string.movie_images))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        reverseLayout = true
    ) {
        items(movieDetails.content.galleries.size + 2) {
            when (it) {
                0, movieDetails.content.galleries.size + 1 -> Spacer(modifier = modifier.width(35.dp))
                else -> MovieImageCard(image = movieDetails.content.galleries[it - 1])
            }
        }
    }
}

@Composable
fun MovieImageCard(modifier: Modifier = Modifier, image: String) {
    Card(
        modifier = modifier
            .width(130.dp)
            .height(73.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}
