package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.FirestoreMovieEntity
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirestoreMovieRepository
import com.example.movieapp.movieList.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FireStoreMovieRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirestoreMovieRepository {
    override suspend fun addToWatchlist(movie: FirestoreMovieEntity) {
        TODO("Not yet implemented")
    }

    override fun getWatchlist(): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }
}











