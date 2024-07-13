package com.example.movieapp.movieList.data.repository

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
    private val firestore: FirebaseFirestore
):AuthenticationRepository {
}