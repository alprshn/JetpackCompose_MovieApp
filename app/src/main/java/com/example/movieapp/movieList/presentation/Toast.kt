package com.example.movieapp.movieList.presentation

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun Toast(message: String){
    makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
}