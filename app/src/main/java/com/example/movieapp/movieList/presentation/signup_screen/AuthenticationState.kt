package com.example.movieapp.movieList.presentation.signup_screen

data class AuthenticationState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String = ""
)