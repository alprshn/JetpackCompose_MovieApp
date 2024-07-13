package com.example.movieapp.movieList.domain.use_cases

import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserAuthenticated @Inject constructor(private val repository: AuthenticationRepository) {
    operator fun invoke(): Boolean {
        return repository.isUserAuthenticatedInFirebase()
    }
}