package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface  RoomDataBaseRepository {
    suspend fun insert(movieEntity: MovieEntity)

    suspend fun delete(movieEntity: MovieEntity)

    suspend fun update(movieEntity: MovieEntity)

    suspend fun getFavoriteMovies(): Flow<List<MovieEntity>>
}