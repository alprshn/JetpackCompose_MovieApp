package com.example.movieapp.movieList.presentation.viewmodel

import MovieMapper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.model.Genre
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val searchRepository: SearchRepository,
    private val roomDatabaseRepository: RoomDataBaseRepository
) : ViewModel() {
    private val currentUser = authRepository.getCurrentUser()
    private val movieMapper = MovieMapper()

    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>> get() = _favoriteMovies

    private val _searchResults: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val searchResults: StateFlow<PagingData<Result>> = _searchResults

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

    fun addFavorite(result: Result) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEntity = movieMapper.mapToMovieEntity(result, user.uid)

                roomDatabaseRepository.insert(movieEntity.copy(userId = user.uid, isFavorite = true))
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
    //Favorites Finish

    //Search
    fun search(query: String) {
        viewModelScope.launch {
            searchRepository.searchMoviePaging(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchResults.value = it
                    Log.d("SearchViewModel", "New data loaded for query: $query")
                }
        }
    }
    //Search Finish

    //Watchlist
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