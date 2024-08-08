package com.example.movieapp.movieList.presentation.watchlist_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.search_data.MovieIdResult
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.model.Genre
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()

    private val _watchlistMovies = MutableLiveData<List<FirebaseMovieEntity>>()
    val watchlistMovies: LiveData<List<FirebaseMovieEntity>> get() = _watchlistMovies


    fun getWatchlistMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                firebaseMovieRepository.getWatchlistMovies(user.uid).collect {
                    _watchlistMovies.postValue(it)
                }
            }
        }
    }


}