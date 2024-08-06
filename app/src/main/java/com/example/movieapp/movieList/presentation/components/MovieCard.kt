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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.darkGreyColor
import com.google.gson.Gson

@Composable
fun MovieCard(navController: NavHostController,movie: Result) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 4.dp)
            .clickable {
                val movieJson = Uri.encode(Gson().toJson(movie))
                navController.navigate(Screens.DetailScreen.route + "/$movieJson")
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .width(130.dp)
                    .background(darkGreyColor)
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            MovieCardDetails(movie = movie)
        }
    }
}

