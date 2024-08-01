package com.example.movieapp.movieList.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.movieList.data.repository.DataStorePreferenceRepository

class DataStoreViewModelFactory(private val dataStorePreferenceRepository: DataStorePreferenceRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataStorePreferenceRepository) as T
        }
        throw IllegalStateException()
    }
}