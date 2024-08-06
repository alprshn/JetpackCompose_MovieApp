package com.example.movieapp.movieList.presentation.settings_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.repository.DataStorePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository
) : ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> get() = _isDarkModeEnabled

    private val _language = MutableLiveData("en")
    var language: LiveData<String> = _language

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

}