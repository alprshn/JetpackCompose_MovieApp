package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.movie.FirestoreMovieEntity
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirestoreMovieRepository {
    // Diğer fonksiyonlar
    suspend fun addToWatchlist(movie: FirestoreMovieEntity)
    fun getWatchlist(): Flow<Resource<List<Movie>>>
}