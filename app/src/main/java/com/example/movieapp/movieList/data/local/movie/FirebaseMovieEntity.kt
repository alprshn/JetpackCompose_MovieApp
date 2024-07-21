package com.example.movieapp.movieList.data.local.movie

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class FirebaseMovieEntity(
    val id: Int? = 0,
    val adult: Boolean,
    val backdrop_path: String? = "",
    val genre_ids: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double,
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val video: Boolean,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
    val addedToWatchlist: Boolean = false // Yeni özellik
)