package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.whiteColor

@Composable
fun MovieLoginText(text:String){
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        fontSize = 18.sp,
        fontFamily = latoFontFamily,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold
    )

}