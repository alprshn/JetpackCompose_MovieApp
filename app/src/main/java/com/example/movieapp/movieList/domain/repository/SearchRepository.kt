package com.example.movieapp.movieList.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.movieList.data.remote.api.response.Genre
import com.example.movieapp.movieList.data.remote.api.response.MovieIdResponse
import com.example.movieapp.movieList.data.remote.api.response.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMoviePaging(query: String, language:String): Flow<PagingData<Result>>
    suspend fun popularMoviePaging(language:String): Flow<PagingData<Result>>
    suspend fun findMovieById(movieId: Int, language:String): Flow<MovieIdResponse>
    suspend fun genreMovieList(language:String): Flow<List<Genre>>
}