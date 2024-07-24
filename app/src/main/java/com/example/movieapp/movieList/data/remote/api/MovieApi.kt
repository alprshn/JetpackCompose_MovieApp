package com.example.movieapp.movieList.data.remote.api

import com.example.movieapp.movieList.data.remote.api.response.SearchMovie
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Header("Authorization") authorization: String
    ): SearchMovie

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

}