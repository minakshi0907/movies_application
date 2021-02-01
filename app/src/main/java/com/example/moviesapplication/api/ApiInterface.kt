package com.example.moviesapplication.api

import com.example.moviesapplication.BuildConfig
import com.example.moviesapplication.model.moviedetails.MovieDetails
import com.example.moviesapplication.model.movies.MovieResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(BuildConfig.GET_MOVIES_URL)
    fun getMoviesList(@Query("page") page: Int): Single<MovieResponse>

    @GET(BuildConfig.MOVIE_DETAILS_URL)
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<MovieDetails>
}