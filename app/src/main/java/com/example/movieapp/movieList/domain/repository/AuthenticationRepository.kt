package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun isUserAuthenticatedInFirebase(): Boolean

    fun getFirebaseAuthState(): Flow<Boolean>

    fun firebaseSignIn(email: String, password: String): Flow<Resource<Boolean>>

    fun firebaseSignOut(): Flow<Resource<Boolean>>

    fun firebaseSignUp(email: String, password: String): Flow<Resource<Boolean>>
}