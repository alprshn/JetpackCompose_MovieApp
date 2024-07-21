package com.example.movieapp.movieList.data.repository


import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseMovieRepository {
    override suspend fun insert(movieEntity: FirebaseMovieEntity) {
        withContext(IO) {
            firestore.collection("watchlist")
                .document(movieEntity.userId)
                .collection("watchlistMovies")
                .document(movieEntity.id.toString())
                .set(movieEntity)
                .await()
        }
    }

    override suspend fun delete(movieEntity: FirebaseMovieEntity) {
        withContext(IO) {
            firestore.collection("watchlist")
                .document(movieEntity.userId)
                .collection("watchlistMovies")
                .document(movieEntity.id.toString())
                .delete()
                .await()
        }
    }

    override suspend fun getMovieById(movieId: Int, userId: String): FirebaseMovieEntity? {
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

    override suspend fun getWatchlistMovies(userId: String): Flow<List<FirebaseMovieEntity>> {
        return flow {
            val querySnapshot = firestore.collection("watchlist")
                .document(userId)
                .collection("watchlistMovies")
                .get()
                .await()
            emit(querySnapshot.documents.mapNotNull { it.toObject<FirebaseMovieEntity>() })
        }
    }

    override suspend fun updateWatchlistStatus(id: Int, userId: String, addedToWatchlist: Boolean) {
        withContext(IO) {
            val movieRef = firestore.collection("watchlist")
                .document(userId)
                .collection("watchlistMovies")
                .document(id.toString())
            movieRef.update("addedToWatchlist", addedToWatchlist).await()
        }
    }

}











