package com.example.movieapp.movieList.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.movieapp.ui.theme.backgroundColor

@Composable
fun FavoritesScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Favorites")
        }
    }
}


@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen()
}