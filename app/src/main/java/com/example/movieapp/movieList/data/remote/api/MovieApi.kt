package com.example.movieapp.movieList.data.remote.api

import com.example.movieapp.movieList.data.remote.api.response.search_data.GenreResult
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.data.remote.api.response.search_data.SearchMovie
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Header("Authorization") authorization: String
    ): SearchMovie

    @GET("movie/popular")
    suspend fun popularMovie(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Header("Authorization") authorization: String
    ): SearchMovie

    @GET("movie/{movie_id}")
    suspend fun findByIdMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Header("Authorization") authorization: String
    ): Result

    @GET("genre/movie/list")
    suspend fun genreMovieList(
        @Query("language") language: String,
        @Header("Authorization") authorization: String
    ): GenreResult


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

}