package com.example.movieapp.movieList.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screens(
    val route: String,
    val icon: ImageVector? = null,
    val title: String? = null
) {
    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("signup_screen")
    object SearchScreen : Screens("search_screen",Icons.Outlined.Search, "Search")
    object DetailScreen : Screens("detail_screen")
    object FavoritesScreen : Screens("favorite_screen", Icons.Outlined.FavoriteBorder, "Favorites")
    object WatchListScreen : Screens("watchlist_screen", Icons.Outlined.Menu, "Watchlist")
    object SplashScreen : Screens("splash_screen")
}
