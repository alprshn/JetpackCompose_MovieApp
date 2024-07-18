package com.example.movieapp.movieList.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.presentation.login_screen.LoginScreen
import com.example.movieapp.ui.theme.backgroundColor


@Composable

fun SearchScreen() {
    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Search")
        }
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}