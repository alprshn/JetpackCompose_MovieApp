package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.MovieDatabase
import com.example.movieapp.movieList.data.mappers.toMovie
import com.example.movieapp.movieList.data.mappers.toMovieEntity
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.domain.repository.MovieListRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : SearchRepository {
    override suspend fun searchQueries(query: String): Flow<Resource<SearchMovie>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val authorization = "Bearer YOUR_API_KEY"
                movieApi.searchMovie(query, authorization)
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
}