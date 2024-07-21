package com.example.movieapp.movieList.presentation.watchlist_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.Genre
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
    val genres = listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
        Genre(80, "Crime"),
        Genre(99, "Documentary"),
        Genre(18, "Drama"),
        Genre(10751, "Family"),
        Genre(14, "Fantasy"),
        Genre(36, "History"),
        Genre(27, "Horror"),
        Genre(10402, "Music"),
        Genre(9648, "Mystery"),
        Genre(10749, "Romance"),
        Genre(878, "Science Fiction"),
        Genre(10770, "TV Movie"),
        Genre(53, "Thriller"),
        Genre(10752, "War"),
        Genre(37, "Western")
    )

    fun getWatchlistMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                firebaseMovieRepository.getWatchlistMovies(user.uid).collect {
                    _watchlistMovies.postValue(it)
                }
            }
        }
    }

    fun addWatchlist(movie: FirebaseMovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.insert(
                    movie.copy(
                        userId = user.uid,
                        addedToWatchlist = true
                    )
                )
                //getWatchlistMovies()
            }
        }
    }


    fun removeWatchlist(movie: FirebaseMovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.updateWatchlistStatus(movie.id!!, user.uid, false)
                //getWatchlistMovies()

            }
        }
    }

    fun isWatchlist(movieId: Int): LiveData<Boolean> {
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