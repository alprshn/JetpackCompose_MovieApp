package com.example.movieapp.movieList.presentation.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.presentation.FavoritesViewModel
import com.example.movieapp.ui.theme.backgroundColor

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {
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
                Log.e("Poster","${movie.poster_path}")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 4.dp)
                ) {
                    Row {

                        Image(
                            painter = rememberImagePainter("https://image.tmdb.org/t/p/original${movie.poster_path}"),
                            contentScale = ContentScale.FillHeight,
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .aspectRatio(0.7f),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                                .padding(16.dp)
                        ) {
                            Text(text = movie.title, color = Color.Black)
                            Text(text = movie.release_date, color = Color.Gray)
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
    FavoritesScreen()
}