package com.example.movieapp.movieList.presentation

data class AuthenticationState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String = ""
)