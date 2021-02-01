package com.example.moviesapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.model.moviedetails.MovieDetails
import com.example.moviesapplication.repository.MovieDetailsRespository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieDetailsRespository: MovieDetailsRespository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    fun getMovieDetails(movieId: Int) {
        movieDetailsRespository.getMovieDetails(compositeDisposable, movieId)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val movieDetails: LiveData<MovieDetails> =
        movieDetailsRespository.movieDetailsData

    fun getState(): LiveData<State> = movieDetailsRespository.state


}