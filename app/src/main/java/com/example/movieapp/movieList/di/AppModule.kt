package com.example.movieapp.movieList.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieapp.movieList.data.local.movie.MovieDatabase
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.data.repository.AuthenticationRepositoryImpl
import com.example.movieapp.movieList.data.repository.FirebaseMovieRepositoryImpl
import com.example.movieapp.movieList.data.repository.RoomDataBaseRepositoryImpl
import com.example.movieapp.movieList.data.repository.SearchRepositoryImpl
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun providesFirebaseRepositoryImpl(firestore: FirebaseFirestore): FirebaseMovieRepository {
        return FirebaseMovieRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()


    @Provides
    @Singleton
    fun providesAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesApiService(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSearchRepository(movieApi: MovieApi): SearchRepository {
        return SearchRepositoryImpl(movieApi = movieApi)
    }

    @Provides
    @Singleton
    fun provideRoomDataBase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movies.sqlite"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabaseRepository(roomdb: MovieDatabase): RoomDataBaseRepository {
        return RoomDataBaseRepositoryImpl(roomdb.movieDao)
    }


}