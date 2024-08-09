package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.MovieDao
import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataBaseRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
) : RoomDataBaseRepository {
    override suspend fun insertMovieEn(movieEntityEn: MovieEntityEn) {
        withContext(IO) {
            dao.insertMovieEn(movieEntityEn)
        }
    }

    override suspend fun insertMovieTr(movieEntityTr: MovieEntityTr) {
        withContext(IO) {
            dao.insertMovieTr(movieEntityTr)
        }
    }

    override suspend fun deleteEn(movieEntityEn: MovieEntityEn) {
        withContext(IO) {
            dao.deleteMovieEn(movieEntityEn.id, movieEntityEn.userId)
        }
    }

    override suspend fun deleteTr(movieEntityTr: MovieEntityTr) {
        withContext(IO) {
            dao.deleteMovieTr(movieEntityTr.id, movieEntityTr.userId)
        }
    }

    override suspend fun getMovieByIdEn(movieId: Int, userId: String): MovieEntityEn? {
        return dao.getMovieByIdEn(movieId, userId)
    }

    override suspend fun getMovieByIdTr(movieId: Int, userId: String): MovieEntityTr? {
        return dao.getMovieByIdTr(movieId, userId)
    }

    override suspend fun getFavoriteMoviesEn(userId: String): Flow<List<MovieEntityEn>> {
        return withContext(IO) {
            dao.getFavoriteMoviesEn(userId)
        }
    }

    override suspend fun getFavoriteMoviesTr(userId: String): Flow<List<MovieEntityTr>> {
        return withContext(IO) {
            dao.getFavoriteMoviesTr(userId)
        }
    }

    override suspend fun updateFavoriteStatusEn(id: Int, userId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatusEn(id, userId, isFavorite)
        if (!isFavorite) {
            dao.deleteMovieEn(id, userId)
        }
    }

    override suspend fun updateFavoriteStatusTr(id: Int, userId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatusTr(id, userId, isFavorite)
        if (!isFavorite) {
            dao.deleteMovieTr(id, userId)
        }
    }

}