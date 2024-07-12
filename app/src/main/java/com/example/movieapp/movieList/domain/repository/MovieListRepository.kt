package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        page: Int,
        includeAdult: Boolean
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    fun getFavoriteMovies(): Flow<Resource<List<Movie>>>
}