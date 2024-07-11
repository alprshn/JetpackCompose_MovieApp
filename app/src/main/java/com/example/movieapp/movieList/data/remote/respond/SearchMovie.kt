package com.example.movieapp.movieList.data.remote.respond

data class SearchMovie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)