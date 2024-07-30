    package com.example.movieapp.movieList.data.remote.api.response.search_data

    data class SearchMovie(
        val page: Int,
        val results: List<Result>,
        val total_pages: Int,
        val total_results: Int
    )