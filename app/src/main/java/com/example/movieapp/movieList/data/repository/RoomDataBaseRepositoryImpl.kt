package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.MovieDao
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
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
            dao.deleteMovie(movieEntity)
        }
    }

    override suspend fun update(movieEntity: MovieEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return withContext(IO) {
            dao.getFavoriteMovies()
        }
    }
}