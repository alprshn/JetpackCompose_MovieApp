package com.example.movieapp.movieList.presentation.settings_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.repository.DataStorePreferenceRepository
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
class SettingsViewModel @Inject constructor(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository,
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> get() = _isDarkModeEnabled

    private val _language = MutableLiveData("en")
    var language: LiveData<String> = _language

    private var _signOutState = MutableStateFlow<AuthenticationState>(value = AuthenticationState())
    val signOutState: StateFlow<AuthenticationState> = _signOutState.asStateFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()

    init {
        viewModelScope.launch {
            dataStorePreferenceRepository.getDarkModeEnabled.collect {
                _isDarkModeEnabled.value = it
            }
        }
        viewModelScope.launch {
            dataStorePreferenceRepository.getLanguage.collect {
                _language.value = it
            }
        }
    }
    fun toggleDarkMode() {
        viewModelScope.launch {
            val newMode = !_isDarkModeEnabled.value
            _isDarkModeEnabled.value = newMode // Yeni dark mode değerini güncelle
            dataStorePreferenceRepository.setDarkModeEnabled(newMode)
        }
    }

    suspend fun saveLanguage(language: String) {
        dataStorePreferenceRepository.setLanguage(language.toString())
    }

    fun getApiLanguage(): String {
        return when (_language.value) {
            "tr" -> "tr-TR"
            else -> "en-US"
        }
    }


    fun signOut() = viewModelScope.launch {
        repository.signOut().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _isUserAuthenticated.update { false }
                    _signOutState.update { it.copy(isSuccess = "Sign out successful!") }
                }

                is Resource.Error -> {
                    Log.e("AuthViewModel", "Sign out failed: ${result.message}")
                    _signOutState.update { it.copy(isError = result.message.toString()) }
                }

                is Resource.Loading -> {
                    _signOutState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun resetSignOutState() {
        _signOutState.update { it.copy(isSuccess = "", isError = "", isLoading = false) }
    }

}