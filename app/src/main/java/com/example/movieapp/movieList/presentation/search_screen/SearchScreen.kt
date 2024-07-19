@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.search_screen


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
import coil.compose.rememberImagePainter
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.searchTextColor


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = viewModel.searchList.value
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

            if (result.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (result.error.isNotBlank()) {
                Log.d("TAG", "MainContent: ${result.error}")
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = viewModel.searchList.value.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            if (result.data.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Log.d("TAG", "MainContent: Your Token")
                    viewModel.searchList.value.data?.let {
                        items(it) {
                            SearchMoviewContentItem(it)
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun SearchMoviewContentItem(result: Result) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {

            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Log.e("Search Image", result.poster_path)
            Log.e("Search Name", result.title)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .height(300.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${result.poster_path}"),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = "${result.title} (${result.release_date.substring(0, 4)})",
                modifier = Modifier
                    .padding(4.dp),
                color = Color.White
            )
        }

    }
}