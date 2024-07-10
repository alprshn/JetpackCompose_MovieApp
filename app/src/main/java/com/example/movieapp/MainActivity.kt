package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.bottomBarSelectedItemColor
import com.example.movieapp.ui.theme.bottomBarUnSelectedItemColor

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Favorites",
                        selectedIcon = Icons.Default.FavoriteBorder,
                        unselectedIcon = Icons.Outlined.FavoriteBorder,
                        hasNews = false
                    ),BottomNavigationItem(
                        title = "Search",
                        selectedIcon = Icons.Default.Search,
                        unselectedIcon = Icons.Outlined.Search,
                        hasNews = false
                    ),BottomNavigationItem(
                        title = "Watchlist",
                        selectedIcon = Icons.Default.Menu,
                        unselectedIcon = Icons.Outlined.Menu,
                        hasNews = false
                    )
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = backgroundColor,
                    bottomBar = {
                        NavigationBar(containerColor = bottomBarColor) {
                            items.forEachIndexed{index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        //navController.navigate(item.title)
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        BadgedBox(badge = {
                                            if (item.badgeCount != null){
                                                Badge{
                                                    Text(text = item.badgeCount.toString())
                                                }
                                            }else if(item.hasNews){
                                                Badge()
                                            }
                                        }) {
                                            Icon(imageVector = if (index == selectedItemIndex){
                                                item.selectedIcon
                                            }else item.unselectedIcon, contentDescription =item.title )

                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = bottomBarSelectedItemColor, // Seçili item icon rengi
                                        unselectedIconColor = bottomBarUnSelectedItemColor, // Seçili olmayan item icon rengi
                                        selectedTextColor = bottomBarSelectedItemColor, // Seçili item text rengi
                                        unselectedTextColor = bottomBarUnSelectedItemColor,  // Seçili olmayan item text rengi
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }) {

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {

    }
}