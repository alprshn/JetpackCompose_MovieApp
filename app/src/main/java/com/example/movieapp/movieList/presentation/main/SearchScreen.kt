@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapp.movieList.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.presentation.login_screen.LoginScreen
import com.example.movieapp.movieList.util.Resource
import com.example.movieapp.ui.theme.backgroundColor



@Composable
fun SearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchState by viewModel.searchState.collectAsState()

    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = { viewModel.search(searchQuery) },
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {
            Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                when (searchState) {
                    is Resource.Loading -> {
                        Text(text = "Loading...")
                    }
                    is Resource.Success -> {
                        val results = (searchState as Resource.Success<SearchMovie>).data?.results
                        results?.forEach { result ->
                            Text(text = result.title)
                        }
                    }
                    is Resource.Error -> {
                        (searchState as Resource.Error).message?.let { Text(text = it) }
                    }
                }
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        searchQuery = "test",
        onSearchQueryChange = {}
    )
}