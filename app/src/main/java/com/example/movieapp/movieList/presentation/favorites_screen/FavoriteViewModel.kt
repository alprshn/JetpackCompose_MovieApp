package com.example.movieapp.movieList.presentation.favorites_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import com.example.movieapp.movieList.data.remote.api.response.MovieIdResponse
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()

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
}