package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.presentation.BottomNavigation
import com.example.movieapp.movieList.presentation.search_screen.SearchScreen
import com.example.movieapp.movieList.presentation.SplashScreen
import com.example.movieapp.movieList.presentation.login_screen.LoginScreen
import com.example.movieapp.movieList.presentation.main.DetailScreen
import com.example.movieapp.movieList.presentation.signup_screen.SignUpScreen
import com.example.movieapp.movieList.presentation.favorites_screen.FavoritesScreen
import com.example.movieapp.movieList.presentation.watchlist_screen.WatchListScreen
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.backgroundColor
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val window = window
                WindowCompat.setDecorFitsSystemWindows(window, false)
                val insetsController = WindowInsetsControllerCompat(window, window.decorView)
                window.statusBarColor =
                    backgroundColor.toArgb() // Status bar rengini burada değiştirebilirsiniz
                insetsController.isAppearanceLightStatusBars = false

                Surface {
                    val bottomBarHeight = 56.dp
                    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
                    var buttonsVisible = remember { mutableStateOf(true) }

                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            if (currentRoute in listOf(
                                    Screens.FavoritesScreen.route,
                                    Screens.SearchScreen.route,
                                    Screens.WatchListScreen.route
                                )
                            ) {
                                BottomNavigation(
                                    navController = navController,
                                    state = remember { mutableStateOf(true) },
                                    modifier = Modifier
                                )
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(backgroundColor)
                        ) {
                            NavigationHost(navController = navController)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(route = Screens.FavoritesScreen.route) {
            FavoritesScreen(navController = navController)
        }
        composable(route = Screens.WatchListScreen.route) {
            WatchListScreen(navController = navController)
        }
        composable(route = Screens.DetailScreen.route + "/{movie}") { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movie")
            val movie = Gson().fromJson(movieJson, Result::class.java)
            DetailScreen(movie = movie)
        }
    }
}
