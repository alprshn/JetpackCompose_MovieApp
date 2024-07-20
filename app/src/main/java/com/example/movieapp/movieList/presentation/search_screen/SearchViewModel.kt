package com.example.movieapp.movieList.presentation.search_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.movieList.data.remote.respond.Result
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
    private val _searchResults: MutableStateFlow<PagingData<Result>> = MutableStateFlow(PagingData.empty())
    val searchResults: StateFlow<PagingData<Result>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            repository.searchMoviePaging(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchResults.value = it
                    Log.d("SearchViewModel", "New data loaded for query: $query")
                }
        }
    }
}
