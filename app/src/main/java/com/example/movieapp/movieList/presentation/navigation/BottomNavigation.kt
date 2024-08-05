package com.example.movieapp.movieList.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.bottomBarIndicatorColor
import com.example.movieapp.ui.theme.bottomBarSelectedItemColor
import com.example.movieapp.ui.theme.bottomBarUnSelectedItemColor


@Composable
fun BottomNavigation(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Screens.FavoritesScreen, Screens.SearchScreen, Screens.WatchListScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor = bottomBarColor,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(text = screen.title?.invoke() ?: "")
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = bottomBarUnSelectedItemColor,
                    selectedTextColor = bottomBarSelectedItemColor,
                    unselectedIconColor = bottomBarUnSelectedItemColor,
                    selectedIconColor = bottomBarSelectedItemColor,
                    indicatorColor = bottomBarIndicatorColor
                ),
            )
        }
    }

}