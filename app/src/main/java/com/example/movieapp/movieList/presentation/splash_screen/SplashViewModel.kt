package com.example.movieapp.movieList.presentation.splash_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {
    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()
    init {
        checkIfUserIsAuthenticated()
    }
    private fun checkIfUserIsAuthenticated() = viewModelScope.launch {
        val isAuthenticated = repository.isUserAuthenticated()
        Log.d("AuthViewModel", "User is authenticated: $isAuthenticated")
        _isUserAuthenticated.update { isAuthenticated }
    }
}