package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity

import kotlinx.coroutines.flow.Flow

interface FirebaseMovieRepository {

    suspend fun insert(movieEntity: FirebaseMovieEntity)

    suspend fun delete(movieEntity: FirebaseMovieEntity)

    //suspend fun update(movieEntity: MovieEntity)
    suspend fun getMovieById(movieId: Int, userId: String): FirebaseMovieEntity?
    suspend fun getWatchlistMovies(userId: String): Flow<List<FirebaseMovieEntity>>
    suspend fun updateWatchlistStatus(id: Int, userId: String, addedToWatchlist: Boolean)
}