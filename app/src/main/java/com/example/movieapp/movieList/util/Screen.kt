package com.example.movieapp.movieList.util

class Screen {
    sealed class Screen(val route: String){
        object LoginScreen: Screen("login_screen")
        object SignUpScreen: Screen("signup_screen")
        object SearchScreen: Screen("search_screen")
        object DetailScreen: Screen("detail_screen")
        object FavoritesScreen: Screen("favorite_screen")
        object WatchListScreen: Screen("watchlist_screen")
    }
}