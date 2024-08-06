package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.data.repository.DataStorePreferenceRepository
import com.example.movieapp.movieList.presentation.settings_screen.SettingsScreen
import com.example.movieapp.movieList.presentation.navigation.BottomNavigation
import com.example.movieapp.movieList.presentation.search_screen.SearchScreen
import com.example.movieapp.movieList.presentation.splash_screen.SplashScreen
import com.example.movieapp.movieList.presentation.login_screen.LoginScreen
import com.example.movieapp.movieList.presentation.detail_screen.DetailScreen
import com.example.movieapp.movieList.presentation.signup_screen.SignUpScreen
import com.example.movieapp.movieList.presentation.favorites_screen.FavoritesScreen
import com.example.movieapp.movieList.presentation.settings_screen.DataStoreViewModelFactory
import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
import com.example.movieapp.movieList.presentation.watchlist_screen.WatchListScreen
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.backgroundColor
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = DataStoreViewModelFactory(DataStorePreferenceRepository(this))
            )
            val currentLanguage = settingsViewModel.language.observeAsState(initial = "en")
            currentLanguage.value?.let { SetLanguage(it) }
            val isDarkModeEnabled by settingsViewModel.isDarkModeEnabled.collectAsState()


            MovieAppTheme(darkTheme = isDarkModeEnabled) {
                val window = window
                WindowCompat.setDecorFitsSystemWindows(window, false)
                val insetsController = WindowInsetsControllerCompat(window, window.decorView)
                window.statusBarColor =
                    backgroundColor.toArgb()
                insetsController.isAppearanceLightStatusBars = false

                Surface {
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
    private fun SetLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
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
            DetailScreen(movie = movie, navController = navController)
        }
        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}
