package com.example.movieapp.movieList.presentation.signup_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.presentation.AuthenticationState
import com.example.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {

    private var _registerState =
        MutableStateFlow<AuthenticationState>(value = AuthenticationState())
    val registerState: StateFlow<AuthenticationState> = _registerState.asStateFlow()


    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()

    init {
        checkIfUserIsAuthenticated()
    }

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email = email, password = password).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _registerState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _registerState.update { it.copy(isSuccess = "Register Successful!") }
                    _isUserAuthenticated.update { true }
                }

                is Resource.Error -> {
                    _registerState.update { it.copy(isError = result.message.toString()) }
                }
            }
        }
    }


    private fun checkIfUserIsAuthenticated() = viewModelScope.launch {
        val isAuthenticated = repository.isUserAuthenticated()
        Log.d("AuthViewModel", "User is authenticated: $isAuthenticated")
        _isUserAuthenticated.update { isAuthenticated }
    }


    fun resetLoadingState() {
        _registerState.update { it.copy(isLoading = false) }
    }

    fun resetErrorState() {
        _registerState.update { it.copy(isError = "") }
    }


}