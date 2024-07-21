@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.presentation.favorites_screen.FavoritesViewModel
import com.example.movieapp.movieList.presentation.watchlist_screen.WatchListViewModel
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor


@Composable
fun DetailScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    viewModelWatchlist: WatchListViewModel = hiltViewModel(),
    movie: Result,
) {

    val isFavorite by viewModel.isFavorite(movie.id).observeAsState(initial = false)
    val isWatchlist by viewModelWatchlist.isWatchlist(movie.id).observeAsState(initial = false)

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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                                    backdrop_path = movie.backdrop_path,
                                    genre_ids = movie.genre_ids.toString(),
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
                                    isFavorite = false
                                )
                            )
                        } else {
                            viewModel.addFavorite(
                                MovieEntity(
                                    id = movie.id,
                                    userId = "", // Kullanıcı kimliğini burada belirtmeyin
                                    adult = movie.adult,
                                    backdrop_path = movie.backdrop_path,
                                    genre_ids = movie.genre_ids.toString(),
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
                                    isFavorite = true
                                )
                            )
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
                            viewModelWatchlist.removeWatchlist(
                                FirebaseMovieEntity(
                                    id = movie.id,
                                    userId = "", // Kullanıcı kimliğini burada belirtmeyin
                                    adult = movie.adult,
                                    backdrop_path = movie.backdrop_path,
                                    genre_ids = movie.genre_ids.toString(),
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
                            viewModelWatchlist.addWatchlist(
                                FirebaseMovieEntity(
                                    id = movie.id,
                                    userId = "", // Kullanıcı kimliğini burada belirtmeyin
                                    adult = movie.adult,
                                    backdrop_path = movie.backdrop_path,
                                    genre_ids = movie.genre_ids.toString(),
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
                                    addedToWatchlist = true
                                )
                            )
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

