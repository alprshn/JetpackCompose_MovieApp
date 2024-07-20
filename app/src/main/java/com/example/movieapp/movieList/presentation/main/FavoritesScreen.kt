package com.example.movieapp.movieList.presentation.main

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
import androidx.navigation.NavController
import com.example.movieapp.movieList.presentation.FavoritesViewModel
import com.example.movieapp.ui.theme.backgroundColor

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {

    val favoriteMovies by viewModel.favoriteMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getFavoriteMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Favorites")
            favoriteMovies.forEach { movie ->
                Text(text = movie.title)
            }
        }
    }
}



@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen()
}