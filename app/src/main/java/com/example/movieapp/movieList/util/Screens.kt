package com.example.movieapp.movieList.util


sealed class Screens(val route: String) {
    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("signup_screen")
    object SearchScreen : Screens("search_screen")
    object DetailScreen : Screens("detail_screen")
    object FavoritesScreen : Screens("favorite_screen")
    object WatchListScreen : Screens("watchlist_screen")
}
