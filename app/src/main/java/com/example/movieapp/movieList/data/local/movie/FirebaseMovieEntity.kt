package com.example.movieapp.movieList.data.local.movie




data class FirebaseMovieEntity(
    val id: Int? = 0,
    val userId: String = "",
    val adult: Boolean = false,
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = emptyList(),
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double = 0.0,
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val video: Boolean = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
    val addedToWatchlist: Boolean = false // Yeni özellik
) {
    // Boş yapıcı
    constructor() : this(
        id = 0,
        userId = "",
        adult = false,
        backdrop_path = "",
        genre_ids = emptyList(),
        original_language = "",
        original_title = "",
        overview = "",
        popularity = 0.0,
        poster_path = "",
        release_date = "",
        title = "",
        video = false,
        vote_average = 0.0,
        vote_count = 0,
        addedToWatchlist = false
    )
}