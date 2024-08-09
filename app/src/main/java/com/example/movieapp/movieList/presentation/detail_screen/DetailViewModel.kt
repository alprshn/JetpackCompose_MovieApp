package com.example.movieapp.movieList.presentation.detail_screen

import MovieMapper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
import com.example.movieapp.movieList.data.remote.api.response.Genre
import com.example.movieapp.movieList.data.remote.api.response.MovieIdResponse
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.domain.repository.FirebaseMovieRepository
import com.example.movieapp.movieList.domain.repository.RoomDataBaseRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
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

    private val _favoriteMoviesEn = MutableLiveData<List<MovieEntityEn>>()
    val favoriteMoviesEn: LiveData<List<MovieEntityEn>> get() = _favoriteMoviesEn

    private val _favoriteMoviesTr = MutableLiveData<List<MovieEntityTr>>()
    val favoriteMoviesTr: LiveData<List<MovieEntityTr>> get() = _favoriteMoviesTr

    private val _movieDetails = MutableLiveData<MovieIdResponse>()
    val movieDetails: LiveData<MovieIdResponse> get() = _movieDetails


    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres

    private suspend fun fetchMovieDetails(movieId: Int, language: String): MovieIdResponse? {
        return try {
            val response = searchRepository.findMovieById(movieId, language).firstOrNull()
            response
        } catch (e: HttpException) {
            Log.e("fetchMovieDetails", "HTTP error: ${e.code()} ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("fetchMovieDetails", "Unknown error: ${e.message}")
            null
        }
    }



    fun addFavorite(movieId: Int) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieDetailsEn = fetchMovieDetails(movieId, "en")
                val movieDetailsTr = fetchMovieDetails(movieId, "tr")

                movieDetailsEn?.let {
                    val movieResultEn = movieMapper.mapMovieIdResponseToResult(it)
                    val movieEntityEn = movieMapper.mapToMovieEntityEn(movieResultEn, user.uid).copy(isFavorite = true)
                    roomDatabaseRepository.insertMovieEn(movieEntityEn)
                    Log.d("addFavorite", "Added movie in English: ${movieEntityEn.title}")
                }

                movieDetailsTr?.let {
                    val movieResultTr = movieMapper.mapMovieIdResponseToResult(it)
                    val movieEntityTr = movieMapper.mapToMovieEntityTr(movieResultTr, user.uid).copy(isFavorite = true)
                    roomDatabaseRepository.insertMovieTr(movieEntityTr)
                    Log.d("addFavorite", "Added movie in Turkish: ${movieEntityTr.title}")
                }
            }
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEn = roomDatabaseRepository.getMovieByIdEn(movieId, user.uid)
                val movieTr = roomDatabaseRepository.getMovieByIdTr(movieId, user.uid)

                movieEn?.let { roomDatabaseRepository.deleteEn(it) }
                movieTr?.let { roomDatabaseRepository.deleteTr(it) }

                Log.d("removeFavorite", "Removed movie with ID: $movieId from favorites in both languages")
            }
        }
    }

    fun isFavorite(movieId: Int): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEn = roomDatabaseRepository.getMovieByIdEn(movieId, user.uid)
                val movieTr = roomDatabaseRepository.getMovieByIdTr(movieId, user.uid)
                isFavorite.postValue(movieEn?.isFavorite == true || movieTr?.isFavorite == true)
                Log.d("isFavorite", "Movie ID: $movieId is favorite in EN: ${movieEn?.isFavorite}, TR: ${movieTr?.isFavorite}")
            }
        }
        return isFavorite
    }
    fun addWatchlist(movieId: Int) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieDetailsEn = fetchMovieDetails(movieId, "en")
                val movieDetailsTr = fetchMovieDetails(movieId, "tr")

                movieDetailsEn?.let {
                    val movieResultEn = movieMapper.mapMovieIdResponseToResult(it)
                    val firebaseMovieEntityEn = movieMapper.mapToFirebaseMovieEntity(movieResultEn, user.uid, "en")
                    firebaseMovieRepository.insert(firebaseMovieEntityEn.copy(addedToWatchlist = true))
                }

                movieDetailsTr?.let {
                    val movieResultTr = movieMapper.mapMovieIdResponseToResult(it)
                    val firebaseMovieEntityTr = movieMapper.mapToFirebaseMovieEntity(movieResultTr, user.uid, "tr")
                    firebaseMovieRepository.insert(firebaseMovieEntityTr.copy(addedToWatchlist = true))
                }
            }
        }
    }

    fun removeWatchlist(movieId: Int) {
        viewModelScope.launch {
            currentUser?.let { user ->
                firebaseMovieRepository.updateWatchlistStatus(movieId, user.uid, false, "en")
                firebaseMovieRepository.updateWatchlistStatus(movieId, user.uid, false, "tr")
            }
        }
    }

    fun isWatchlist(movieId: Int): LiveData<Boolean> {
        val addedToWatchlist = MutableLiveData<Boolean>()
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEn = firebaseMovieRepository.getMovieById(movieId, user.uid, "en")
                val movieTr = firebaseMovieRepository.getMovieById(movieId, user.uid, "tr")
                addedToWatchlist.postValue(movieEn?.addedToWatchlist == true || movieTr?.addedToWatchlist == true)
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


    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val movieEn = roomDatabaseRepository.getMovieByIdEn(movieId, user.uid)
                val movieTr = roomDatabaseRepository.getMovieByIdTr(movieId, user.uid)

                if (movieEn != null || movieTr != null) {
                    // Favori ise kaldır
                    movieEn?.let { roomDatabaseRepository.updateFavoriteStatusEn(movieId, user.uid, false) }
                    movieTr?.let { roomDatabaseRepository.updateFavoriteStatusTr(movieId, user.uid, false) }
                    Log.d("toggleFavorite", "Removed movie with ID: $movieId from favorites")
                } else {
                    // Favori değilse ekle
                    addFavorite(movieId)
                    Log.d("toggleFavorite", "Added movie with ID: $movieId to favorites")
                }
            }
        }
    }

}