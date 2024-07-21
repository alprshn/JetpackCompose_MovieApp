package com.example.movieapp.movieList.presentation.favorites_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class FavoritesViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository
) : ViewModel() {
    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>> get() = _favoriteMovies

    private val currentUser = authRepository.getCurrentUser()

    fun getFavoriteMovies() {
        currentUser?.let { user ->
            viewModelScope.launch {
                roomDatabaseRepository.getFavoriteMovies(user.uid).collect {
                    _favoriteMovies.postValue(it)
                }
            }
        }
    }

    fun addFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                roomDatabaseRepository.insert(movie.copy(userId = user.uid, isFavorite = true))
              // getFavoriteMovies()
            }
        }
    }

    fun removeFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            currentUser?.let { user ->
                roomDatabaseRepository.updateFavoriteStatus(movie.id, user.uid, false)
               //getFavoriteMovies()
            }
        }
    }


    fun isFavorite(movieId: Int): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            currentUser?.let { user ->
                val movie = roomDatabaseRepository.getMovieById(movieId,user.uid)
                isFavorite.postValue(movie?.isFavorite == true)
            }
        }
        return isFavorite
    }


}