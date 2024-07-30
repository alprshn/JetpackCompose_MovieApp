package com.example.movieapp.movieList.presentation.settings_screen

import android.util.Log
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

    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> get() = _selectedLanguage

    init {
        viewModelScope.launch {
            dataStorePreferenceRepository.getLanguage.collect { language ->
                _selectedLanguage.value = language
            }
        }
    }
    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            dataStorePreferenceRepository.setLanguage(languageCode)
            _selectedLanguage.value = languageCode
            Log.e("selectedLanguage viewModel", languageCode)
        }
    }
    fun getApiLanguage(): String {
        return when (_selectedLanguage.value) {
            "tr" -> "tr-TR"
            else -> "en-US"
        }
    }

}