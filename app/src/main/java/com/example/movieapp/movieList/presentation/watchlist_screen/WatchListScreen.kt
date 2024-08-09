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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapp.R
import com.example.movieapp.movieList.presentation.components.MovieCard
import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.latoFontFamily
import com.google.gson.Gson


@Composable
fun WatchListScreen(viewModel: WatchListViewModel = hiltViewModel(), navController: NavHostController,settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val watchlistMovies by viewModel.watchlistMovies.observeAsState(emptyList())
    val currentLanguage = settingsViewModel.language.observeAsState().value


    LaunchedEffect(currentLanguage) {
        viewModel.getWatchlistMovies(currentLanguage ?: "en")
    }
    Log.e("WatchListScreen", watchlistMovies.toString())
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.watchlist),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = latoFontFamily,
                modifier = Modifier.padding(top = 16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
            ) {
                items(watchlistMovies) { movie ->
                    val movie = MovieMapper().firestoreMapToResult(movie)
                    MovieCard(navController = navController, movie = movie, onClick = {
                        val movieJson = Uri.encode(Gson().toJson(movie))
                        navController.navigate(Screens.DetailScreen.route + "/$movieJson")
                    })
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