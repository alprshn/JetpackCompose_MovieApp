package com.example.movieapp.movieList.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.movieList.presentation.BottomNavigationItem
import com.example.movieapp.movieList.presentation.BottomNavigationMenu

@Composable
fun WatchListScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "WatchList")
        }
        BottomNavigationMenu(selectedItem = BottomNavigationItem.WATCHLIST, navController = navController)
    }
}