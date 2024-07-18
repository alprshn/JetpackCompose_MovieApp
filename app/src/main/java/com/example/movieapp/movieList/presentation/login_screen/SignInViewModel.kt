package com.example.movieapp.movieList.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class SignInViewModel@Inject constructor(private val repository: AuthenticationRepository) : ViewModel() {
    private var _signInState = MutableStateFlow<SignInState>(value = SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email = email, password = password).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _signInState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _signInState.update { it.copy(isSuccess = "Sign In Successful!") }
                }

                is Resource.Error -> {
                    _signInState.update { it.copy(isError = result.message.toString()) }
                }
            }
        }
    }
}