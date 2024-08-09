package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.movieList.data.remote.api.response.Result

@Composable
fun MovieRowList(
    title: String,
    movies: List<Result>,
    navController: NavHostController
) {
    MovieSectionTitle(title = title)
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie, navController)
        }
    }
}