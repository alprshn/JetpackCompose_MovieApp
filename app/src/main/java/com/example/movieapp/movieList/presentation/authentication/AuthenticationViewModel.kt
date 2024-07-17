package com.example.movieapp.movieList.presentation.authentication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.domain.use_cases.AuthenticationUseCases
import com.example.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authUseCases: AuthenticationUseCases) :
    ViewModel() {
    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signInState: State<Resource<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signUpState: State<Resource<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signOutState: State<Resource<Boolean>> = _signOutState

    private val _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState: State<Boolean> = _firebaseAuthState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignIn(email = email,password = password).collect {
                _signInState.value = it
                Log.e("TAG", "signIn: ${_signInState.value.data}")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignUp(email = email, password = password).collect {
                _signUpState.value = it
            }
        }
    }

    fun signOut(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignOut().collect {
                _signOutState.value = it
                if (it == Resource.Success(true)) {
                    _signInState.value = Resource.Success(false)
                }
            }
        }
    }

    fun getFirebaseAuthState() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState().collect {
                _firebaseAuthState.value = it
            }
        }
    }
}