@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.search_screen


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.searchTextColor
import com.google.gson.Gson


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navController: NavHostController) {
    val query: MutableState<String> = remember { mutableStateOf("") }
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
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
                    focusedBorderColor = bottomBarColor,  // Odaklanmış durumda çerçeve rengi
                    unfocusedBorderColor = bottomBarColor,  // Odaklanmamış durumda çerçeve rengi
                    cursorColor = Color.White,  // İmleç rengi
                    focusedTextColor = Color.White,  // Metin rengi
                    disabledTextColor = searchTextColor,
                    containerColor = bottomBarColor,
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)

            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchResults.itemCount) { index ->
                    searchResults[index]?.let {
                        SearchMovieContentItem(it, navController)
                    }
                }

                searchResults.apply {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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


            }
        }
    }
}
    @Composable
    fun SearchMovieContentItem(movie: Result, navController: NavHostController) {
        if (movie == null){
            Log.e("Search Movie", "Movie is null")
            return
        }

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
                        .height(300.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
//                val releaseYear = if (movie.release_date.isNotEmpty() && movie.release_date.length >= 4) {
//                    movie.release_date.substring(0, 4)
//                } else {
//                    "Unknown"
//                }
//                Text(
//                    text = "${movie.title} ($releaseYear)",
//                    modifier = Modifier
//                        .padding(4.dp),
//                    color = Color.White
//                )
            }

        }
    }
