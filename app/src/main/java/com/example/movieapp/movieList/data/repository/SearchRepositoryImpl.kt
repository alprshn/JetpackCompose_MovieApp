package com.example.movieapp.movieList.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.movieList.data.remote.api.MovieApi
import com.example.movieapp.movieList.data.remote.api.response.Genre
import com.example.movieapp.movieList.data.remote.api.response.MovieIdResponse
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.domain.repository.SearchRepository
import com.example.movieapp.movieList.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : SearchRepository {
    override fun searchMoviePaging(query: String, language: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchApiPagingSource(movieApi, query, language) }
        ).flow
    }

    override suspend fun popularMoviePaging(language: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularApiPagingSource(movieApi, language) }
        ).flow
    }

    override suspend fun findMovieById(
        movieId: Int,
        language: String
    ): Flow<MovieIdResponse> {
        return flow {
            movieApi.findByIdMovie(movieId, language, Constants.API_KEY)
                .let { emit(it) }
        }
    }

    override suspend fun genreMovieList(language: String): Flow<List<Genre>> {
        return flow {
            val genreResponse = movieApi.genreMovieList(language, Constants.API_KEY)
            emit(genreResponse.genres)

        }
    }
}