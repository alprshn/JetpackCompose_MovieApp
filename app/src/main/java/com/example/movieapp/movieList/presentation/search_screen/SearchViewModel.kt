package com.example.movieapp.movieList.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {
    private val _searchState = MutableStateFlow<Resource<SearchMovie>>(Resource.Loading())
    val searchState: StateFlow<Resource<SearchMovie>> = _searchState.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            repository.searchQueries(query).collectLatest { result ->
                _searchState.value = result
            }
        }
    }
}