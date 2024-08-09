package com.example.movieapp.movieList.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.util.Screens
import com.google.gson.Gson

@Composable
fun MovieItem(movie: Result, navController: NavHostController) {

    Box(
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .width(130.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp)) // Gölge eklemek için shadow kullanın
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                val movieJson = Uri.encode(Gson().toJson(movie))
                navController.navigate(Screens.DetailScreen.route + "/$movieJson")
            },
    ) {
        Image(
            painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
