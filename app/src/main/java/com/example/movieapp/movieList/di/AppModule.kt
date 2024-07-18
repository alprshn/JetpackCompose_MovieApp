package com.example.movieapp.movieList.di

import com.example.movieapp.movieList.data.repository.AuthenticationRepositoryImpl
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
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


}