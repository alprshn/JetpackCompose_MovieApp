package com.example.movieapp.movieList.presentation.watchlist_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository
) : ViewModel() {
    private val _watchlistMovies = MutableLiveData<List<FirebaseMovieEntity>>()
    val watchlistMovies: LiveData<List<FirebaseMovieEntity>> get() = _watchlistMovies

    private val currentUser = authRepository.getCurrentUser()


    fun getWatchlistMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                firebaseMovieRepository.getWatchlistMovies(user.uid).collect {
                    _watchlistMovies.postValue(it)
                }
            }
        }
    }

    fun addFavorite(movie: FirebaseMovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.insert(movie.copy(userId = user.uid, addedToWatchlist = true))
            }
        }
    }


    fun removeFavorite(movie: FirebaseMovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.updateWatchlistStatus(movie.id!!, user.uid, false)
            }
        }
    }

    fun isFavorite(movieId: Int): LiveData<Boolean> {
        val addedToWatchlist = MutableLiveData<Boolean>()
        viewModelScope.launch {
            currentUser?.let { user ->
                val movie = firebaseMovieRepository.getMovieById(movieId, user.uid)
                addedToWatchlist.postValue(movie?.addedToWatchlist == true)

            }
        }
        return addedToWatchlist
    }
}