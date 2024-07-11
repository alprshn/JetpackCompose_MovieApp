package com.example.movieapp.movieList.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1,
        @Header("Authorization") authorization: String
    )

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

}