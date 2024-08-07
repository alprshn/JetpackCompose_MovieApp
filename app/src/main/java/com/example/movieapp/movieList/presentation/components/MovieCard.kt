package com.example.movieapp.movieList.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.presentation.detail_screen.DetailViewModel
import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
import com.example.movieapp.movieList.util.Screens
import com.google.gson.Gson

@Composable
fun MovieCard(navController: NavHostController, movie: Result, onClick: () -> Unit, viewModel: DetailViewModel = hiltViewModel(), settingsViewModel: SettingsViewModel= hiltViewModel()) {
    val genres by viewModel.genres.observeAsState(initial = emptyList())
    val currentLanguage = settingsViewModel.language.observeAsState().value
    LaunchedEffect(currentLanguage) {
        viewModel.getGenre(currentLanguage.toString())
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .width(130.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            MovieCardDetails(movie = movie, genres = genres)
        }
    }
}

