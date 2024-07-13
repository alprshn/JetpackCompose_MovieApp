package com.example.movieapp.movieList.domain.use_cases

import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(private val repository: AuthenticationRepository) {
    operator fun invoke(email: String, password: String) = repository.firebaseSignUp(email, password)
}