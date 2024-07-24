package com.example.movieapp.movieList.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.util.Converters

@Database(
    entities = [MovieEntity::class],
    version = 2,
)
@TypeConverters(Converters::class)

abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}