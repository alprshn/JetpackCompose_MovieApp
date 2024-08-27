package com.example.movieapp.movieList.presentation.search_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity

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
class SearchViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val searchRepository: SearchRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository,
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()
    private val _searchResults: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val searchResults: StateFlow<PagingData<Result>> = _searchResults

    private val _popularMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val popularMovies: StateFlow<PagingData<Result>> = _popularMovies

    private val _watchlistMovies = MutableLiveData<List<FirebaseMovieEntity>>()
    val watchlistMovies: LiveData<List<FirebaseMovieEntity>> get() = _watchlistMovies

    private val _favoriteMoviesEn = MutableLiveData<List<MovieEntityEn>>()
    val favoriteMoviesEn: LiveData<List<MovieEntityEn>> get() = _favoriteMoviesEn

    private val _favoriteMoviesTr = MutableLiveData<List<MovieEntityTr>>()
    val favoriteMoviesTr: LiveData<List<MovieEntityTr>> get() = _favoriteMoviesTr

    fun getFavoriteMovies(language: String) {
        currentUser?.let { user ->
            viewModelScope.launch {
                if (language == "en") {
                    roomDatabaseRepository.getFavoriteMoviesEn(user.uid).collect {
                        _favoriteMoviesEn.postValue(it)
                    }
                } else {
                    roomDatabaseRepository.getFavoriteMoviesTr(user.uid).collect {
                        _favoriteMoviesTr.postValue(it)
                    }
                }
            }
        }
    }



    fun search(query: String, language: String) {
        viewModelScope.launch {
            searchRepository.searchMoviePaging(query, language)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchResults.value = it
                    Log.d("SearchViewModel", "New data loaded for query: $query")
                }
        }
    }

    fun popularMovies(language: String) {
        viewModelScope.launch {
            searchRepository.popularMoviePaging(language)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _popularMovies.value = it
                    Log.d("MainViewModel", "Popular movies loaded")
                }
        }
    }


    fun getWatchlistMovies(language: String) {
        currentUser?.let { user ->
            viewModelScope.launch {
                firebaseMovieRepository.getWatchlistMovies(user.uid, language).collect {
                    _watchlistMovies.postValue(it)
                }
            }
        }
    }

}