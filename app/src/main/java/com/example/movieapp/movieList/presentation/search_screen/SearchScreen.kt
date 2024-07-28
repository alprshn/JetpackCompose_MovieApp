@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.search_screen


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.presentation.viewmodel.MainViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.searchTextColor
import com.google.gson.Gson


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val query: MutableState<String> = remember { mutableStateOf("") }
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(pageCount = { popularMovies.itemCount })

    LaunchedEffect(Unit) {
        viewModel.popularMovies()
        Log.d("SearchScreen", "Loading popular movies...")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                OutlinedTextField(
                    value = query.value,
                    onValueChange = {
                        query.value = it
                        viewModel.search(query.value)
                    },
                    enabled = true,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    },
                    placeholder = { Text(text = "Search Movie", color = searchTextColor) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = bottomBarColor,
                        unfocusedBorderColor = bottomBarColor,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        disabledTextColor = searchTextColor,
                        containerColor = bottomBarColor,
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .padding(end = 8.dp)
                )

                IconButton(
                    onClick = {
                        navController.navigate(Screens.SettingsScreen.route)
                    },
                    modifier = Modifier.size(56.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White,
                        containerColor = bottomBarColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White,
                    )
                }
            }

            if (query.value.isEmpty()) {
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    popularMovies[page]?.let {
                        PopularMovieItem(it, navController)
                    }

                }


                popularMovies.apply {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(58.dp)
                            )
                        }

                        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                            Text(text = "Error loading popular movies")
                        }

                        loadState.refresh is LoadState.NotLoading -> {
                        }
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    searchResults.apply {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            modifier = Modifier.size(58.dp)
                                        )
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                item {
                                    Text(text = "Error")
                                }
                            }

                            loadState.refresh is LoadState.NotLoading -> {
                            }
                        }

                    }
                    items(searchResults.itemCount) { index ->
                        searchResults[index]?.let {
                            SearchMovieContentItem(it, navController)
                        }
                    }


                }
            }


        }
    }
}

@Composable
fun PopularMovieItem(movie: Result, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
            .clickable {
                val movieJson = Uri.encode(Gson().toJson(movie))
                navController.navigate(Screens.DetailScreen.route + "/$movieJson")
            },
        colors = CardDefaults.cardColors(containerColor = bottomBarColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bottomBarColor)
        ) {
            Image(
                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun SearchMovieContentItem(movie: Result, navController: NavHostController) {

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                val movieJson = Uri.encode(Gson().toJson(movie))
                Log.e("MovieJson", movieJson)
                navController.navigate(Screens.DetailScreen.route + "/$movieJson")
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .height(275.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            val releaseYear =
                if (movie.release_date.isNullOrEmpty() || movie.release_date.length < 4) {
                    "Unknown"
                } else {
                    movie.release_date.substring(0, 4)
                }
            Text(
                text = "${movie.title} (${releaseYear})",
                modifier = Modifier
                    .padding(4.dp),
                color = Color.White
            )
        }

    }
}
