package com.example.movieapp.movieList.presentation.detail_screen

import MovieMapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.model.Genre
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val searchRepository: SearchRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository,
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()
    private val movieMapper = MovieMapper()

    private val _watchlistMovies = MutableLiveData<List<FirebaseMovieEntity>>()
    val watchlistMovies: LiveData<List<FirebaseMovieEntity>> get() = _watchlistMovies

    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>> get() = _favoriteMovies

    fun getFavoriteMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                roomDatabaseRepository.getFavoriteMovies(user.uid).collect {
                    _favoriteMovies.postValue(it)
                }
            }
        }
    }

    fun addFavorite(result: Result) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEntity = movieMapper.mapToMovieEntity(result, user.uid)

                roomDatabaseRepository.insert(
                    movieEntity.copy(
                        userId = user.uid,
                        isFavorite = true
                    )
                )
            }
        }
    }

    fun removeFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                roomDatabaseRepository.updateFavoriteStatus(movie.id, user.uid, false)
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

    fun getWatchlistMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                firebaseMovieRepository.getWatchlistMovies(user.uid).collect {
                    _watchlistMovies.postValue(it)
                }
            }
        }
    }

    fun addWatchlist(result: Result) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val firebaseMovieEntity = movieMapper.mapToFirebaseMovieEntity(result, user.uid)

                firebaseMovieRepository.insert(
                    firebaseMovieEntity.copy(
                        userId = user.uid,
                        addedToWatchlist = true
                    )
                )
            }
        }
    }

    fun removeWatchlist(movie: FirebaseMovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.updateWatchlistStatus(movie.id!!, user.uid, false)
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

}