package com.example.movieapp.movieList.presentation.detail_screen

import MovieMapper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.search_data.Genre
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.data.remote.api.response.search_data.SpokenLanguage
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
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


    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres

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

    fun getGenre(language: String) {
        viewModelScope.launch {
            try {
                searchRepository.genreMovieList(language).collect { genreList ->
                    _genres.postValue(genreList)
                }
            } catch (e: HttpException) {
                Log.e("DetailViewModel", "HTTP error: ${e.code()} ${e.message()}")
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Unknown error: ${e.message}")
            }
        }
    }

}