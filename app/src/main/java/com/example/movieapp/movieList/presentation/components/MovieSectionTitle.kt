package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.whiteColor

@Composable
fun MovieSectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onPrimary,
        fontFamily = latoFontFamily,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
    )
}