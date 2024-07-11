package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.MovieDatabase
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.domain.repository.MovieListRepository
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
            val shouldFetchFromRemote =
                forceFetchFromRemote && movieDatabase.movieDao.getMovieById(page) == null
            if (shouldFetchFromRemote) {
                emit(Resource.Loading(false))
                return@flow
            }
            val movieListFromApi = try {
                val authorization = "Bearer YOUR_API_KEY"
                movieApi.searchMovie(false, page, authorization)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
            }

            val movieEntities = movieListFromApi.results.map { it.toMovieEntity() }

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        TODO("Not yet implemented")
    }
}