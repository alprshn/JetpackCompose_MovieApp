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

    @Query("DELETE FROM favorite_movies WHERE id = :id AND userId = :userId")
    suspend fun deleteMovie(id: Int, userId: String)


    @Query("SELECT * FROM favorite_movies WHERE id = :id AND userId = :userId")
    suspend fun getMovieById(id: Int, userId: String): MovieEntity?

    @Query("SELECT * FROM favorite_movies WHERE userId = :userId")
    fun getFavoriteMovies(userId: String): Flow<List<MovieEntity>>

    @Query("UPDATE favorite_movies SET isFavorite = :isFavorite WHERE id = :id AND userId = :userId")
    suspend fun updateFavoriteStatus(id: Int, userId: String, isFavorite: Boolean)
}