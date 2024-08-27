package com.example.movieapp.movieList.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.presentation.signup_screen.AuthenticationState
import com.example.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {


    private var _signInState = MutableStateFlow<AuthenticationState>(value = AuthenticationState())
    val signInState: StateFlow<AuthenticationState> = _signInState.asStateFlow()


    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()

    init {
        checkIfUserIsAuthenticated()
    }


    fun loginUser(email: String, password: String, emailVerificationErrorMessage: String, loginErrorMessage: String, emailVerificationMessage: String,) = viewModelScope.launch {
        repository.loginUser(email = email, password = password).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _signInState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    repository.isEmailVerified().collect { verificationResult ->
                        Log.d(
                            "AuthViewModel",
                            "Email verification result: ${verificationResult.data}"
                        )
                        Log.d(
                            "AuthViewModel",
                            "Email verification result: ${repository.isEmailVerified()}"
                        )

                        when (verificationResult) {
                            is Resource.Error -> {
                                _signInState.update { it.copy(isError =emailVerificationErrorMessage) }
                            }

                            is Resource.Loading -> {
                                _signInState.update { it.copy(isLoading = true) }
                            }

                            is Resource.Success -> {
                                Log.d(
                                    "AuthViewModel",
                                    "Email success verification result: ${verificationResult.data}"
                                )
                                if (verificationResult.data == true) {
                                    _signInState.update { it.copy(isSuccess = "Login Successful!") }
                                    _isUserAuthenticated.update { true }
                                } else {
                                    _signInState.update { it.copy(isError =emailVerificationMessage) }
                                    _isUserAuthenticated.update { false }
                                }
                            }
                        }
                    }

                }

                is Resource.Error -> {
                    _signInState.update { it.copy(isError = loginErrorMessage) }
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
        _signInState.update { it.copy(isLoading = false) }
    }

    fun resetErrorState() {
        _signInState.update { it.copy(isError = "") }
    }

}