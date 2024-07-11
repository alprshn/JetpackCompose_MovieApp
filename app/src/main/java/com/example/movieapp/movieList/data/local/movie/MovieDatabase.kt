package com.example.movieapp.movieList.data.local.movie

import androidx.room.Database

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase {

}