package com.example.movieapp.movieList.data.local.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1,
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
    companion object {
        var INSTANCE : MovieDatabase? = null

        fun getMovieDatabase(context: Context):MovieDatabase?{
            if (INSTANCE == null){
                synchronized(MovieDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movies.sqlite").createFromAsset("movies.sqlite").build()
                }
            }
            return INSTANCE
        }
    }
}