package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.MovieDatabase
import com.example.movieapp.movieList.data.mappers.toMovie
import com.example.movieapp.movieList.data.mappers.toMovieEntity
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.domain.repository.MovieListRepository
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.movieapp.movieList.data.mappers.toMovieEntity
import kotlinx.coroutines.flow.map


class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        page: Int,
        includeAdult: Boolean,
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val authorization = "Bearer YOUR_API_KEY"
                val movieListFromApi = movieApi.searchMovie(includeAdult, page, authorization).results
                val movies = movieListFromApi.map { it.toMovieEntity().toMovie() }
                emit(Resource.Success(movies))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't load data due to network failure"))
            } catch (e: HttpException) {
                emit(Resource.Error("Couldn't load data due to server response error"))
            } catch (e: Exception) {
                emit(Resource.Error("An unexpected error occurred"))
            }
            emit(Resource.Loading(false))
        }
    }


    override fun getFavoriteMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        try {

        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch favorites"))
        }
        emit(Resource.Loading(false))
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading(true))
        try {
            val movie = movieDatabase.movieDao.getMovieById(id)?.toMovie()
            if (movie != null) {
                emit(Resource.Success(movie))
            } else {
                emit(Resource.Error("Movie not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch movie"))
        }
        emit(Resource.Loading(false))
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        val movieEntity = movieDatabase.movieDao.getMovieById(id)
        val updatedMovieEntity = movieEntity?.copy(isFavorite = isFavorite) ?: return // Eğer film bulunamazsa fonksiyon sonlanır.
        if (isFavorite) {
            movieDatabase.movieDao.upsertMovie(updatedMovieEntity)
        } else {
            movieDatabase.movieDao.deleteMovie(updatedMovieEntity)
        }
    }
}