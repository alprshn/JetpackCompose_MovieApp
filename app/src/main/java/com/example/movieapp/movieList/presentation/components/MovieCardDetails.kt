package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.movieList.data.remote.api.response.search_data.Genre
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.presentation.detail_screen.DetailViewModel
import com.example.movieapp.ui.theme.latoFontFamily

@Composable
fun MovieCardDetails(movie: Result,genres: List<Genre>) {

    Column(
        modifier = Modifier
            .fillMaxSize(
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, top = 2.dp)
    ) {
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 2.dp),
            fontSize = 18.sp,
            fontFamily = latoFontFamily
        )
        MovieCardItems(icon = Icons.Outlined.StarBorder, text = String.format("%.1f", movie.vote_average), color = MaterialTheme.colorScheme.tertiaryContainer)
        MovieCardItems(icon = Icons.Outlined.ConfirmationNumber, text = movie.genre_ids
            .mapNotNull { id -> genres.find { it.id == id }?.name }
            .joinToString(", "), color = MaterialTheme.colorScheme.onPrimary)
        val releaseYear =
            if (movie.release_date.isNullOrEmpty() || movie.release_date.length < 4) {
                "Unknown"
            } else {
                movie.release_date.substring(0, 4)
            }
        MovieCardItems(icon = Icons.Outlined.CalendarToday, text = releaseYear, color = MaterialTheme.colorScheme.onPrimary)
    }
}