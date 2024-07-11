package com.example.movieapp.movieList.data.mappers

import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.domain.model.Movie

fun MovieEntity.toMovie(): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}