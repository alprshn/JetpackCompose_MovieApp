package com.example.movieapp.movieList.presentation.watchlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapp.movieList.presentation.favorites_screen.FavoritesViewModel
import com.example.movieapp.ui.theme.backgroundColor


@Composable
fun WatchListScreen(viewModel: WatchListViewModel = hiltViewModel(), navController: NavHostController) {

    val watchlistMovies by viewModel.watchlistMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getWatchlistMovies()
    }

    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "WatchList")
        }
    }
}

@Preview
@Composable
fun WatchListScreenPreview() {
    WatchListScreen()
}