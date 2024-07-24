package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.SearchMovie

interface DetailRepository {
    suspend fun getMovieFromApi(movieId: Int, authorization: String): SearchMovie?
    suspend fun getMovieFromRoom(movieId: Int, userId: String): MovieEntity?
    suspend fun getMovieFromFirebase(movieId: Int, userId: String): FirebaseMovieEntity?
    suspend fun updateFavoriteStatus(movieId: Int, userId: String, isFavorite: Boolean)
    suspend fun updateWatchlistStatus(movieId: Int, userId: String, addedToWatchlist: Boolean)

}