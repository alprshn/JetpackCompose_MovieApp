package com.example.movieapp.movieList.data.repository


import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
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
            val collectionPath =
                "watchlist/${movieEntity.userId}/watchlistMovies_${movieEntity.language}"
            firestore.collection(collectionPath)
                .document(movieEntity.id.toString())
                .set(movieEntity)
                .await()
        }
    }

    override suspend fun delete(movieEntity: FirebaseMovieEntity) {
        withContext(IO) {
            val collectionPath =
                "watchlist/${movieEntity.userId}/watchlistMovies_${movieEntity.language}"
            firestore.collection(collectionPath)
                .document(movieEntity.id.toString())
                .delete()
                .await()
        }
    }

    override suspend fun getMovieById(
        movieId: Int,
        userId: String,
        language: String
    ): FirebaseMovieEntity? {
        return withContext(IO) {
            val collectionPath = "watchlist/$userId/watchlistMovies_$language"
            val documentSnapshot = firestore.collection(collectionPath)
                .document(movieId.toString())
                .get()
                .await()
            documentSnapshot.toObject<FirebaseMovieEntity>()
        }
    }


    override suspend fun getWatchlistMovies(
        userId: String,
        language: String
    ): Flow<List<FirebaseMovieEntity>> {
        return flow {
            val collectionPath = "watchlist/$userId/watchlistMovies_$language"
            val querySnapshot = firestore.collection(collectionPath)
                .get()
                .await()
            emit(querySnapshot.documents.mapNotNull { it.toObject<FirebaseMovieEntity>() })
        }
    }

    override suspend fun updateWatchlistStatus(
        movieId: Int,
        userId: String,
        addedToWatchlist: Boolean,
        language: String
    ) {
        withContext(IO) {
            val collectionPath = "watchlist/$userId/watchlistMovies_$language"
            val movieRef = firestore.collection(collectionPath).document(movieId.toString())
            if (addedToWatchlist) {
                movieRef.update("addedToWatchlist", true).await()
            } else {
                movieRef.delete().await()
            }
        }
    }

}











