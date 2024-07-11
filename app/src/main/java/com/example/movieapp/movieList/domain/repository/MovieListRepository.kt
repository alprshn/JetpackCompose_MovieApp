package com.example.movieapp.movieList.domain.repository

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        page: Int
    ): Flow<List<Movie>>
}