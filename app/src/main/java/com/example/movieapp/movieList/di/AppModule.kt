package com.example.movieapp.movieList.di

import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.data.repository.AuthenticationRepositoryImpl
import com.example.movieapp.movieList.data.repository.SearchRepositoryImpl
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.domain.use_cases.FirebaseAuthState
import com.example.movieapp.movieList.domain.use_cases.FirebaseSignIn
import com.example.movieapp.movieList.domain.use_cases.FirebaseSignOut
import com.example.movieapp.movieList.domain.use_cases.FirebaseSignUp
import com.example.movieapp.movieList.domain.use_cases.IsUserAuthenticated
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
    fun providesAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesApiService(): MovieApi =
        Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun providesSearchRepository(movieApi: MovieApi): SearchRepository {
        return SearchRepositoryImpl(movieApi)
    }

}