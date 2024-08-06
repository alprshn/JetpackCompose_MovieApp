package com.example.movieapp.movieList.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "LanguageData")

class DataStorePreferenceRepository(context: Context) {
    private val dataStore = context.dataStore
    private val defaultLanguage = "en"

    companion object {
        val PREF_LANGUAGE = stringPreferencesKey("language")
        private val PREF_DARK_MODE = stringPreferencesKey("dark_mode")
        private var INSTANCE: DataStorePreferenceRepository? = null

        fun getInstance(context: Context): DataStorePreferenceRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = DataStorePreferenceRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PREF_LANGUAGE] = language
        }
    }

    val getLanguage: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PREF_LANGUAGE] ?: defaultLanguage
        }

    suspend fun setDarkModeEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREF_DARK_MODE] = enabled.toString()
        }
    }

    val getDarkModeEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PREF_DARK_MODE]?.toBoolean() ?: false
        }
}


