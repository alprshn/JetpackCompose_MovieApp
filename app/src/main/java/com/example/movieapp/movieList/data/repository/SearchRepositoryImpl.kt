package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.data.local.movie.MovieDatabase
import com.example.movieapp.movieList.data.mappers.toMovie
import com.example.movieapp.movieList.data.mappers.toMovieEntity
import com.example.movieapp.movieList.data.remote.MovieApi
import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.domain.repository.MovieListRepository
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.util.Constants
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
        try {
            val result = movieApi.searchMovie(query = query, authorization = Constants.API_KEY)
            Resource.Success(data = result)
        } catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }
}