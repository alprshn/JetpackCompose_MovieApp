package com.example.movieapp.movieList.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import kotlinx.coroutines.flow.Flow

@Dao
    interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieEn(movie: MovieEntityEn)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieTr(movie: MovieEntityTr)

    @Query("DELETE FROM favorite_movies_en WHERE id = :id AND userId = :userId")
    suspend fun deleteMovieEn(id: Int, userId: String)

    @Query("DELETE FROM favorite_movies_tr WHERE id = :id AND userId = :userId")
    suspend fun deleteMovieTr(id: Int, userId: String)

    @Query("SELECT * FROM favorite_movies_en WHERE id = :id AND userId = :userId")
    suspend fun getMovieByIdEn(id: Int, userId: String): MovieEntityEn?

    @Query("SELECT * FROM favorite_movies_tr WHERE id = :id AND userId = :userId")
    suspend fun getMovieByIdTr(id: Int, userId: String): MovieEntityTr?

    @Query("SELECT * FROM favorite_movies_en WHERE userId = :userId")
    fun getFavoriteMoviesEn(userId: String): Flow<List<MovieEntityEn>>

    @Query("SELECT * FROM favorite_movies_tr WHERE userId = :userId")
    fun getFavoriteMoviesTr(userId: String): Flow<List<MovieEntityTr>>

    @Query("UPDATE favorite_movies_en SET isFavorite = :isFavorite WHERE id = :id AND userId = :userId")
    suspend fun updateFavoriteStatusEn(id: Int, userId: String, isFavorite: Boolean)

    @Query("UPDATE favorite_movies_tr SET isFavorite = :isFavorite WHERE id = :id AND userId = :userId")
    suspend fun updateFavoriteStatusTr(id: Int, userId: String, isFavorite: Boolean)

}