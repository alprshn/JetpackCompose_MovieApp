package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.presentation.main.SearchScreen
import com.example.movieapp.movieList.presentation.SplashScreen

import com.example.movieapp.movieList.presentation.login_screen.LoginScreen
import com.example.movieapp.movieList.presentation.login_screen.SignInViewModel
import com.example.movieapp.movieList.presentation.signup_screen.SignUpScreen
import com.example.movieapp.movieList.presentation.main.FavoritesScreen
import com.example.movieapp.movieList.presentation.main.WatchListScreen
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MovieApp(navController)

                }
            }
        }
    }
}

@Composable
fun MovieApp(navController: NavHostController) {
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
    }
}

