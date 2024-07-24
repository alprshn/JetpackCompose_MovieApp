package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun getCurrentUser(): FirebaseUser?
    fun isUserAuthenticated(): Boolean
    suspend fun signOut(): Flow<Resource<Unit>>
}