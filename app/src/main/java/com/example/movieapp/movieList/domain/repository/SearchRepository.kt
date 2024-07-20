package com.example.movieapp.movieList.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMoviePaging(query: String): Flow<PagingData<Result>>
}