package com.example.moviesapplication.model.movies

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int


)