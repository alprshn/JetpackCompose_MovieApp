package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.respond.Result

interface DetailRepository {
    suspend fun getMovieFromApi(movieId: Int): Result
    suspend fun getMovieFromRoom(movieId: Int): MovieEntity?
    suspend fun getMovieFromFirebase(movieId: Int, userId: String): FirebaseMovieEntity?
    suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)
    suspend fun updateWatchlistStatus(movieId: Int, userId: String, addedToWatchlist: Boolean)

}