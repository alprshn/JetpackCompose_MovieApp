package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.MovieDao
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.MovieApi
import com.example.movieapp.movieList.data.remote.api.response.SearchMovie
import com.example.movieapp.movieList.domain.repository.DetailRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val apiService: MovieApi,
    private val movieDao: MovieDao,
    private val firestore: FirebaseFirestore
) : DetailRepository {
    override suspend fun getMovieFromApi(movieId: Int, authorization: String): SearchMovie? {
        return try {
            apiService.searchMovie(query = movieId.toString(), page = 1, authorization = authorization)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMovieFromRoom(movieId: Int, userId: String): MovieEntity? {
        return movieDao.getMovieById(movieId, userId)

    }

    override suspend fun getMovieFromFirebase(movieId: Int, userId: String): FirebaseMovieEntity? {
        return withContext(IO) {
            val documentSnapshot = firestore.collection("watchlist")
                .document(userId)
                .collection("watchlistMovies")
                .document(movieId.toString())
                .get()
                .await()
            documentSnapshot.toObject<FirebaseMovieEntity>()
        }
    }

    override suspend fun updateFavoriteStatus(movieId: Int, userId: String, isFavorite: Boolean) {
        movieDao.updateFavoriteStatus(movieId, userId, isFavorite)
        if (!isFavorite) {
            movieDao.deleteMovie(movieId, userId)
        }
    }

    override suspend fun updateWatchlistStatus(
        movieId: Int,
        userId: String,
        addedToWatchlist: Boolean
    ) {
        withContext(IO) {
            val movieRef = firestore.collection("watchlist")
                .document(userId)
                .collection("watchlistMovies")
                .document(movieId.toString())
            if (addedToWatchlist) {
                movieRef.update("addedToWatchlist", true).await()
            } else {
                movieRef.delete().await()
            }
        }
    }

}