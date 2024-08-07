package com.example.movieapp.movieList.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorite_movies_en")
data class MovieEntityEn(
    @PrimaryKey @ColumnInfo(name = "id") @NotNull val id: Int,
    @ColumnInfo(name = "userId") @NotNull val userId: String = "",
    @ColumnInfo(name = "adult") @NotNull val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") @NotNull val backdrop_path: String,
    @ColumnInfo(name = "genre_ids") @NotNull    val genre_ids: List<Int> = emptyList(),
    @ColumnInfo(name = "original_language") @NotNull val original_language: String,
    @ColumnInfo(name = "original_title") @NotNull val original_title: String,
    @ColumnInfo(name = "overview") @NotNull val overview: String,
    @ColumnInfo(name = "popularity") @NotNull val popularity: Double,
    @ColumnInfo(name = "poster_path") @NotNull val poster_path: String,
    @ColumnInfo(name = "release_date") @NotNull val release_date: String,
    @ColumnInfo(name = "title") @NotNull val title: String,
    @ColumnInfo(name = "video") @NotNull val video: Boolean,
    @ColumnInfo(name = "vote_average") @NotNull val vote_average: Double,
    @ColumnInfo(name = "vote_count") @NotNull val vote_count: Int,
    @ColumnInfo(name = "isFavorite", defaultValue = "0") var isFavorite: Boolean,
    @ColumnInfo(name = "language") @NotNull val language: String
)

@Entity(tableName = "favorite_movies_tr")
data class MovieEntityTr(
    @PrimaryKey @ColumnInfo(name = "id") @NotNull val id: Int,
    @ColumnInfo(name = "userId") @NotNull val userId: String = "",
    @ColumnInfo(name = "adult") @NotNull val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") @NotNull val backdrop_path: String,
    @ColumnInfo(name = "genre_ids") @NotNull    val genre_ids: List<Int> = emptyList(),
    @ColumnInfo(name = "original_language") @NotNull val original_language: String,
    @ColumnInfo(name = "original_title") @NotNull val original_title: String,
    @ColumnInfo(name = "overview") @NotNull val overview: String,
    @ColumnInfo(name = "popularity") @NotNull val popularity: Double,
    @ColumnInfo(name = "poster_path") @NotNull val poster_path: String,
    @ColumnInfo(name = "release_date") @NotNull val release_date: String,
    @ColumnInfo(name = "title") @NotNull val title: String,
    @ColumnInfo(name = "video") @NotNull val video: Boolean,
    @ColumnInfo(name = "vote_average") @NotNull val vote_average: Double,
    @ColumnInfo(name = "vote_count") @NotNull val vote_count: Int,
    @ColumnInfo(name = "isFavorite", defaultValue = "0") var isFavorite: Boolean,
    @ColumnInfo(name = "language") @NotNull val language: String
)
