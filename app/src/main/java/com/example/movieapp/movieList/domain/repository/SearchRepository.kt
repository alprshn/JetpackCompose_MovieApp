package com.example.movieapp.movieList.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.movieList.data.remote.api.response.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMoviePaging(query: String): Flow<PagingData<Result>>
}