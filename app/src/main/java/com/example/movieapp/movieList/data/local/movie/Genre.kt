package com.example.movieapp.movieList.data.local.movie

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Genre(
    val id: Int,
    val name: String
)

// Yardımcı fonksiyon: JSON dizisini diziye dönüştürmek için
fun fromJsonToList(json: String): List<Int> {
    val listType = object : TypeToken<List<Int>>() {}.type
    return Gson().fromJson(json, listType)
}

// Yardımcı fonksiyon: Diziyi JSON dizisine dönüştürmek için
fun fromListToJson(list: List<Int>): String {
    return Gson().toJson(list)
}
