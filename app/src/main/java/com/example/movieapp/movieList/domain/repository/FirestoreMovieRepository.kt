package com.example.movieapp.movieList.domain.repository

interface FirestoreMovieRepository {
    interface MovieListRepository {
        // Diğer fonksiyonlar
        suspend fun addToWatchlist(movie: MovieEntity)
        fun getWatchlist(): Flow<Resource<List<Movie>>>
    }
}