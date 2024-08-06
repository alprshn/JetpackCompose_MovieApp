package com.example.movieapp.movieList.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.latoFontFamily


@Composable
fun DetailMovieCardText(text: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onPrimary,
        fontFamily = latoFontFamily
    )
}
