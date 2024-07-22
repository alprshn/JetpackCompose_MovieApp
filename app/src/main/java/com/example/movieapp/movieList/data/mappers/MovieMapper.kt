package com.example.movieapp.movieList.data.mappers

import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.respond.Result
import com.example.movieapp.movieList.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun fromJsonToList(json: String): List<Int> {
    val type = object : TypeToken<List<Int>>() {}.type
    return Gson().fromJson(json, type)
}

fun fromListToJson(list: List<Int>): String {
    return Gson().toJson(list)
}

fun Result.toDomainModel(): Movie {
    return Movie(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        genre_ids = this.genre_ids,
        id = this.id,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}

fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        genre_ids = fromJsonToList(this.genre_ids),
        id = this.id,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}

fun FirebaseMovieEntity.toDomainModel(): Movie {
    return Movie(
        adult = this.adult,
        backdrop_path = this.backdrop_path ?: "",
        genre_ids = fromJsonToList(this.genre_ids ?: ""),
        id = this.id ?: 0,
        original_language = this.original_language ?: "",
        original_title = this.original_title ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity,
        poster_path = this.poster_path ?: "",
        release_date = this.release_date ?: "",
        title = this.title ?: "",
        video = this.video,
        vote_average = this.vote_average ?: 0.0,
        vote_count = this.vote_count ?: 0
    )
}