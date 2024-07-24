package com.example.movieapp.movieList.presentation.viewmodel

data class AuthenticationState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String = ""
)