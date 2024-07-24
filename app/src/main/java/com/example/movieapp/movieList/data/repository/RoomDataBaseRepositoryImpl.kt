package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.MovieDao
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataBaseRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
) : RoomDataBaseRepository {
    override suspend fun insert(movieEntity: MovieEntity) {
        withContext(IO) {
            dao.insertMovie(movieEntity)
        }
    }

    override suspend fun delete(movieEntity: MovieEntity) {
        withContext(IO) {
            dao.deleteMovie(movieEntity.id, movieEntity.userId)
        }
    }

    override suspend fun getMovieById(movieId: Int, userId: String): MovieEntity? {
        return dao.getMovieById(movieId, userId)
    }

    override suspend fun getFavoriteMovies(userId: String): Flow<List<MovieEntity>> {
        return withContext(IO) {
            dao.getFavoriteMovies(userId)
        }
    }
    override suspend fun updateFavoriteStatus(id: Int, userId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, userId, isFavorite)
        if (!isFavorite) {
            dao.deleteMovie(id, userId)
        }
    }

}