package com.example.movieapp.movieList.data.local.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 2,
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}