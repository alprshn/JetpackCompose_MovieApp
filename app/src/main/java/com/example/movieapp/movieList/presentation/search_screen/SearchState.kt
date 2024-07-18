package com.example.movieapp.movieList.presentation.search_screen

import com.example.movieapp.movieList.data.remote.respond.SearchMovie

data class SearchState(
    val isLoading: Boolean = false,
    val data: List<SearchMovie> = emptyList(),
    val error: String = ""
)