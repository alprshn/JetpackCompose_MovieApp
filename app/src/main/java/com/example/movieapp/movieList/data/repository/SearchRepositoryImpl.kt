package com.example.movieapp.movieList.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.movieList.data.remote.api.MovieApi
import com.example.movieapp.movieList.data.remote.api.response.Result
import com.example.movieapp.movieList.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : SearchRepository {
    override fun searchMoviePaging(query: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchApiPagingSource(movieApi, query) }
        ).flow
    }
}