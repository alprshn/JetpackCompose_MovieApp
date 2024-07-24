package com.example.movieapp.movieList.presentation.search_screen

import com.example.movieapp.movieList.data.remote.api.response.Result

data class SearchState(
    val isLoading: Boolean = false,
    val data: List<Result> = emptyList(),
    val error: String = ""
)