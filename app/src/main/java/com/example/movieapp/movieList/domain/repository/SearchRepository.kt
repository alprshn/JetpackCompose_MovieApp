package com.example.movieapp.movieList.domain.repository

import com.example.movieapp.movieList.data.remote.respond.SearchMovie
import com.example.movieapp.movieList.domain.model.Movie
import com.example.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchQueries(query: String): Resource<SearchMovie>
}