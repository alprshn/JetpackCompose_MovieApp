package com.example.movieapp.movieList.presentation.search_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
    val searchList: MutableState<SearchState> = mutableStateOf(SearchState())

    fun search(query: String) = viewModelScope.launch {
        val result = repository.searchQueries(query)
        when(result){
            is Resource.Loading -> {
                searchList.value = SearchState(isLoading = true)
            }

            is Resource.Error ->{
                searchList.value = SearchState(error = result.message.toString())
            }
            is Resource.Success ->{
                result.data?.results?.let {
                    searchList.value = SearchState(data = it)
                }
            }
        }
    }
}
