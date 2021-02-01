package com.example.moviesapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.moviesapplication.base.MoviesApp
import com.example.moviesapplication.model.moviedetails.Genre
import com.example.moviesapplication.model.moviedetails.SpokenLanguage

object AppUtils {

    fun List<Genre>.genreToCommaSeparatedString(): String {
        return this.joinToString(", ", transform = { it.name })
    }

    fun List<SpokenLanguage>.languageToCommaSeparatedString(): String {
        return this.joinToString(", ", transform = { it.name })
    }

    fun checkInternetConnection(): Boolean {
        val connectivityManager =
            MoviesApp.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }


}