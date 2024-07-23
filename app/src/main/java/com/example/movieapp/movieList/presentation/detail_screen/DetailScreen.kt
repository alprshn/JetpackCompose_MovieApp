@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.detail_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.presentation.MainViewModel
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.poppinsFontFamily


@Composable
fun DetailScreen(
    viewModel: MainViewModel = hiltViewModel(), movie: Result, navController: NavHostController
) {

    val isFavorite by viewModel.isFavorite(movie.id).observeAsState(initial = false)
    val isWatchlist by viewModel.isWatchlist(movie.id).observeAsState(initial = false)

    var currentFavoriteState by remember { mutableStateOf(isFavorite) }
    var currentWatchlistState by remember { mutableStateOf(isWatchlist) }

    LaunchedEffect(isWatchlist) {
        currentWatchlistState = isWatchlist
    }
    LaunchedEffect(isFavorite) {
        currentFavoriteState = isFavorite
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.backdrop_path}"),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Text(text = "Buton")
                    }
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)) {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = movie.original_title,
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = latoFontFamily
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp) // Burada padding ekliyoruz
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween, // Ekleme
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally // Ekleme
                        ) {
                            Text(
                                text = "Release Date",
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = latoFontFamily

                            )
                            Text(
                                text = movie.release_date,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color(0xFFBCBCBC),
                                fontFamily = latoFontFamily
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Genre",
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = latoFontFamily
                            )
                            Text(
                                text = movie.getGenreIds()
                                    .mapNotNull { id -> viewModel.genres.find { it.id == id }?.name }
                                    .joinToString(", "),
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = latoFontFamily
                            )

                        }
                    }

                    Text(
                        text = "About Movie",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontFamily = latoFontFamily
                    )
                    Text(
                        text = movie.overview,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = latoFontFamily
                    )
                    Row(
                        modifier = Modifier
                            .background(color = bottomBarColor, shape = RoundedCornerShape(50))
                            .padding(8.dp)
                            .width(200.dp)
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = {
                                Log.e("mEHABA", movie.title)
                                if (currentFavoriteState) {
                                    viewModel.removeFavorite(
                                        MovieEntity(
                                            id = movie.id,
                                            userId = "", // Kullanıcı kimliğini burada belirtmeyin
                                            adult = movie.adult,
                                            backdrop_path = movie.backdrop_path ?: "Unknown",
                                            genre_ids = movie.genre_ids,
                                            original_language = movie.original_language
                                                ?: "Unknown",
                                            original_title = movie.original_title ?: "Unknown",
                                            overview = movie.overview ?: "Unknown",
                                            popularity = movie.popularity ?: 0.0,
                                            poster_path = movie.poster_path ?: "Unknown",
                                            release_date = movie.release_date ?: "Unknown",
                                            title = movie.title ?: "Unknown",
                                            video = movie.video ?: false,
                                            vote_average = movie.vote_average ?: 0.0,
                                            vote_count = movie.vote_count ?: 0,
                                            isFavorite = false
                                        )
                                    )
                                } else {
                                    viewModel.addFavorite(movie)
                                }
                                currentFavoriteState = !currentFavoriteState
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = if (currentFavoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (currentFavoriteState) Color.Red else Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(40.dp)
                                .background(Color.Gray)
                        )
                        IconButton(
                            onClick = {
                                Log.e("mEHABA", movie.title)
                                if (currentWatchlistState) {
                                    viewModel.removeWatchlist(
                                        FirebaseMovieEntity(
                                            id = movie.id,
                                            userId = "", // Kullanıcı kimliğini burada belirtmeyin
                                            adult = movie.adult,
                                            backdrop_path = movie.backdrop_path,
                                            genre_ids = movie.genre_ids,
                                            original_language = movie.original_language,
                                            original_title = movie.original_title,
                                            overview = movie.overview,
                                            popularity = movie.popularity,
                                            poster_path = movie.poster_path,
                                            release_date = movie.release_date,
                                            title = movie.title,
                                            video = movie.video,
                                            vote_average = movie.vote_average,
                                            vote_count = movie.vote_count,
                                            addedToWatchlist = false
                                        )
                                    )
                                } else {
                                    viewModel.addWatchlist(movie)
                                }
                                currentWatchlistState = !currentWatchlistState
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = if (currentWatchlistState) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Watchlist",
                                    tint = if (currentWatchlistState) Color.Blue else Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

