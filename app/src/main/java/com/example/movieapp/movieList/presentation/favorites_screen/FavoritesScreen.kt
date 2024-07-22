package com.example.movieapp.movieList.presentation.favorites_screen

import MovieMapper
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.presentation.MainViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.starColor
import com.google.gson.Gson

@Composable
fun FavoritesScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {
    val favoriteMovies by viewModel.favoriteMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getFavoriteMovies()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(favoriteMovies) { movie ->
                val movie = MovieMapper().roomMapToResult(movie)

                Log.e("Poster", "${movie.poster_path}")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
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
                    ) {
                            Image(
                                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                                contentScale = ContentScale.FillHeight,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(20.dp))

                            )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = movie.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row {
                                Icon(modifier = Modifier.padding(end = 4.dp), tint = starColor, imageVector = Icons.Outlined.StarBorder, contentDescription =null)
                                Text(
                                    text = "${movie.vote_average}",
                                    color = starColor,
                                    fontSize = 14.sp
                                )
                            }

                            Row {
                                Icon(modifier = Modifier.padding(end = 4.dp), imageVector = Icons.Outlined.ConfirmationNumber, contentDescription =null, tint = Color.White)
                                Text(
                                    text = movie.genre_ids.toString(),
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }

                            Row {
                                Icon(modifier = Modifier.padding(end = 4.dp), tint = Color.White, imageVector = Icons.Outlined.CalendarToday, contentDescription =null)
                                Text(
                                    text = movie.release_date.substring(0, 4),
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
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
fun FavoritesScreenPreview() {
    //FavoritesScreen(navController: NavController)
}