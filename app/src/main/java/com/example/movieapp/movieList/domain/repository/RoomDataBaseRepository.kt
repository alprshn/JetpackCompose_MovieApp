package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface RoomDataBaseRepository {
    suspend fun insert(movieEntity: MovieEntity)

    suspend fun delete(movieEntity: MovieEntity)

    //suspend fun update(movieEntity: MovieEntity)
    suspend fun getMovieById(movieId: Int, userId: String): MovieEntity?
    suspend fun getFavoriteMovies(userId: String): Flow<List<MovieEntity>>
    suspend fun updateFavoriteStatus(id: Int, userId: String, isFavorite: Boolean)
}