package com.example.movieapp.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
    interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies():  Flow<List<MovieEntity>>

    @Query("UPDATE favorite_movies SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}