package com.example.movieapp.movieList.presentation.search_screen

import MovieMapper
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
class SearchViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val searchRepository: SearchRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository,
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()

    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>> get() = _favoriteMovies

    private val _movieDetails = MutableLiveData<MovieIdResult?>()
    val movieDetails: LiveData<MovieIdResult?> = _movieDetails

    private val _searchResults: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val searchResults: StateFlow<PagingData<Result>> = _searchResults

    private val _popularMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val popularMovies: StateFlow<PagingData<Result>> = _popularMovies

    private val _watchlistMovies = MutableLiveData<List<FirebaseMovieEntity>>()
    val watchlistMovies: LiveData<List<FirebaseMovieEntity>> get() = _watchlistMovies


    //Favorites
    fun getFavoriteMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                roomDatabaseRepository.getFavoriteMovies(user.uid).collect {
                    _favoriteMovies.postValue(it)
                }
            }
        }
    }


    fun isFavorite(movieId: Int): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            currentUser?.let { user ->
                val movie = roomDatabaseRepository.getMovieById(movieId, user.uid)
                isFavorite.postValue(movie?.isFavorite == true)
            }
        }
        return isFavorite
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

    fun findMovieById(movieId: Int, language: String) {
        viewModelScope.launch {
            val movieDetails = searchRepository.findMovieById(movieId, language)
            movieDetails.firstOrNull()?.let {
                _movieDetails.postValue(it)
                Log.d("MainViewModel", "Movie details loaded for ID: $movieId")
            } ?: Log.e("MainViewModel", "No details found for movie ID: $movieId")
        }
    }

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