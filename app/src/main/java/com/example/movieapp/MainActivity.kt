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
import com.example.movieapp.movieList.presentation.authentication.AuthenticationViewModel
import com.example.movieapp.movieList.presentation.authentication.LoginScreen
import com.example.movieapp.movieList.presentation.authentication.SignUpScreen
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
               Surface (color = MaterialTheme.colorScheme.background){
                   val navController = rememberNavController()
                   val authViewModel : AuthenticationViewModel = hiltViewModel()
                   MovieApp(navController, authenticationViewModel = authViewModel)

                }
            }
        }
    }
}

@Composable
fun MovieApp(navController: NavHostController, authenticationViewModel: AuthenticationViewModel) {
    NavHost(navController = navController , startDestination = Screens.LoginScreen.route){
        composable(route = Screens.LoginScreen.route){
            LoginScreen(navController = navController, authenticationViewModel)
        }
        composable(route = Screens.SignUpScreen.route){
            SignUpScreen(navController, authenticationViewModel)
        }
        composable(route = Screens.SplashScreen.route){
            SplashScreen(navController = navController,authenticationViewModel )
        }
        composable(route = Screens.SearchScreen.route){
            SearchScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {

        val navController = rememberNavController()
        val authViewModel: AuthenticationViewModel = hiltViewModel()
        LoginScreen(navController = navController, authViewModel)

    }
}