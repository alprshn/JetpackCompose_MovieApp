package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.whiteColor


@Composable
fun MovieCardItems(icon: ImageVector, text: String , color: Color) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 6.dp)
                .size(23.dp),
            imageVector = icon,
            contentDescription = null,
            tint = color,
        )
        Text(
            text = text,
            color = color,
            fontSize = 15.sp,
            fontFamily = latoFontFamily
        )
    }
}
