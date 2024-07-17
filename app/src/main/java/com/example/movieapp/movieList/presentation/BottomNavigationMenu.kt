package com.example.movieapp.movieList.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieapp.movieList.util.Screens

enum class BottomNavigationMenu(val icon: ImageVector, val route: Screens) {
    FAVORITES(Icons.Default.FavoriteBorder, Screens.FavoritesScreen),
    SEARCH(Icons.Default.Search, Screens.SearchScreen),
    WATCHLIST(Icons.Default.Menu, Screens.WatchListScreen)
}

@Composable
fun BottomNavigationMenu(
    items: List<BottomNavigationMenu>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavigationMenu) -> Unit
) {