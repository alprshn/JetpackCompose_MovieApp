package com.example.movieapp.movieList.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.movieapp.R


sealed class Screens(
    val route: String,
    val icon: ImageVector? = null,
    @get:Composable val title: @Composable () -> String? = { null }
) {
    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("signup_screen")
    object SearchScreen : Screens("search_screen",Icons.Outlined.Home, { stringResource(id = R.string.home)})
    object DetailScreen : Screens("detail_screen")
    object FavoritesScreen : Screens("favorite_screen", Icons.Outlined.FavoriteBorder,  { stringResource(id = R.string.favorites)})
    object WatchListScreen : Screens("watchlist_screen", Icons.Outlined.BookmarkBorder,  { stringResource(id = R.string.watchlist)})
    object SplashScreen : Screens("splash_screen")
    object SettingsScreen : Screens("settings_screen")
}
