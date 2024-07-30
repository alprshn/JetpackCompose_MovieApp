package com.example.movieapp.movieList.presentation.watchlist_screen

import MovieMapper
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.R
import com.example.movieapp.movieList.presentation.viewmodel.MainViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.starColor
import com.google.gson.Gson


@Composable
fun WatchListScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {

    val watchlistMovies by viewModel.watchlistMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getWatchlistMovies()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.watchlist),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = latoFontFamily,
                modifier = Modifier.padding(top = 16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.background(backgroundColor),
            ) {
                items(watchlistMovies) { movie ->
                    val movie = MovieMapper().firestoreMapToResult(movie)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(vertical = 4.dp)
                            .clickable {
                                val movieJson = Uri.encode(Gson().toJson(movie))
                                Log.e("MovieJson", movieJson)
                                navController.navigate(Screens.DetailScreen.route + "/$movieJson")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(200.dp)
                                .background(backgroundColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .width(130.dp)
                                    .background(Color.DarkGray)
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                                    contentScale = ContentScale.FillHeight,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                )
                            }


                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(backgroundColor)
                                    .padding(start = 10.dp, top = 2.dp)
                            ) {
                                Text(
                                    text = movie.title,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily
                                )
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(23.dp),
                                        tint = starColor,
                                        imageVector = Icons.Outlined.StarBorder,
                                        contentDescription = null
                                    )
                                    Text(
                                        text = String.format("%.1f", movie.vote_average),
                                        color = starColor,
                                        fontSize = 15.sp,
                                        fontFamily = latoFontFamily
                                    )
                                }

                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(23.dp),
                                        imageVector = Icons.Outlined.ConfirmationNumber,
                                        contentDescription = null,
                                        tint = Color.White,

                                        )
                                    Text(
                                        text = movie.getGenreIds()
                                            .mapNotNull { id -> viewModel.genres.find { it.id == id }?.name }
                                            .joinToString(", "),
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontFamily = latoFontFamily
                                    )
                                }

                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(23.dp),
                                        tint = Color.White,
                                        imageVector = Icons.Outlined.CalendarToday,
                                        contentDescription = null
                                    )
                                    val releaseYear =
                                        if (movie.release_date.isNullOrEmpty() || movie.release_date.length < 4) {
                                            "Unknown"
                                        } else {
                                            movie.release_date.substring(0, 4)
                                        }
                                    Text(
                                        text = releaseYear,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontFamily = latoFontFamily
                                    )
                                }


                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WatchListScreenPreview() {
    // WatchListScreen()
}