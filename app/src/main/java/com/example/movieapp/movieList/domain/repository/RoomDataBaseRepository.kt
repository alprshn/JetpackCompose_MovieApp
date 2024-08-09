package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import kotlinx.coroutines.flow.Flow

interface RoomDataBaseRepository {

    suspend fun insertMovieEn(movieEntityEn: MovieEntityEn)
    suspend fun insertMovieTr(movieEntityTr: MovieEntityTr)
    suspend fun deleteEn(movieEntityEn: MovieEntityEn)
    suspend fun deleteTr(movieEntityTr: MovieEntityTr)
    suspend fun getMovieByIdEn(movieId: Int, userId: String): MovieEntityEn?
    suspend fun getMovieByIdTr(movieId: Int, userId: String): MovieEntityTr?
    suspend fun getFavoriteMoviesEn(userId: String): Flow<List<MovieEntityEn>>
    suspend fun getFavoriteMoviesTr(userId: String): Flow<List<MovieEntityTr>>
    suspend fun updateFavoriteStatusEn(movieId: Int, userId: String, isFavorite: Boolean)
    suspend fun updateFavoriteStatusTr(movieId: Int, userId: String, isFavorite: Boolean)

}